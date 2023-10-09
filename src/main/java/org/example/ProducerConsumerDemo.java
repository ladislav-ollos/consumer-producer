package org.example;

import org.example.command.Command;
import org.example.command.CreateUserCommand;
import org.example.command.DeleteAllUsersCommand;
import org.example.command.PrintAllUsersCommand;
import org.example.queue.Consumer;
import org.example.queue.FifoQueue;
import org.example.queue.Producer;
import org.example.repository.Repository;
import org.example.domain.User;
import org.example.repository.UserRepositoryImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ProducerConsumerDemo
{
    private final List<Consumer> consumers = new LinkedList<>();
    private final List<Producer> producers = new LinkedList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final CountDownLatch producerCountDownLatch;

    public ProducerConsumerDemo() {
        LinkedList<Command<User>>
                commands = new LinkedList<>();
        commands.add(new CreateUserCommand(new User(1, "a1", "Robert")));
        commands.add(new CreateUserCommand(new User(2, "a2", "Martin")));
        commands.add(new PrintAllUsersCommand());
        commands.add(new DeleteAllUsersCommand());
        commands.add(new PrintAllUsersCommand());
        Repository<User> userRepository = new UserRepositoryImpl();
        producerCountDownLatch = new CountDownLatch(1);
        FifoQueue<Command<User>> queue = new FifoQueue<>();
        producers.add(new Producer(queue, commands, producerCountDownLatch));
        consumers.add(new Consumer(queue, userRepository));
        //
    }

    public void start() {
        for (Producer p : producers) {
            executorService.submit(p);
        }
        for (Consumer c : consumers) {
            executorService.submit(c);
        }
    }

    public void process() {
        while (!Thread.interrupted() && producerCountDownLatch.getCount() > 0) {
            try {
                producerCountDownLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // at this point there is no more input coming
        // we terminate consumers
        System.out.println("Now we can finish consuming.");
        consumers.forEach(Consumer::terminate);
    }

    public void end() {
        shutdownExecutorService(executorService);
        HibernateUtil.shutdown();
    }

    private void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public static void main( String[] args ) {
        ProducerConsumerDemo producerConsumerDemo = new ProducerConsumerDemo();
        producerConsumerDemo.start();
        producerConsumerDemo.process();
        producerConsumerDemo.end();
    }
}
