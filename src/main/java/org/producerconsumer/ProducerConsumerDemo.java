package org.producerconsumer;

import org.producerconsumer.command.Command;
import org.producerconsumer.command.CreateUserCommand;
import org.producerconsumer.command.DeleteAllUsersCommand;
import org.producerconsumer.command.PrintAllUsersCommand;
import org.producerconsumer.queue.Consumer;
import org.producerconsumer.queue.FifoQueue;
import org.producerconsumer.queue.Producer;
import org.producerconsumer.repository.Repository;
import org.producerconsumer.domain.User;
import org.producerconsumer.repository.UserRepositoryImpl;

import java.util.LinkedList;
import java.util.concurrent.*;

public class ProducerConsumerDemo
{
    private final Consumer consumer;
    private final Producer producer;

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
        producer = new Producer(queue, commands, producerCountDownLatch);
        consumer = new Consumer(queue, userRepository);
    }

    public void start() {
        executorService.submit(producer);
        executorService.submit(consumer);
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
        consumer.terminate();
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
