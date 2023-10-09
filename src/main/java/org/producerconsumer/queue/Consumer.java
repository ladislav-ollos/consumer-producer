package org.producerconsumer.queue;

import org.producerconsumer.command.Command;
import org.producerconsumer.domain.User;
import org.producerconsumer.repository.Repository;

import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {

    private final FifoQueue<Command<User>> queue;
    private final Repository<User> repository;
    private final AtomicBoolean isTerminated = new AtomicBoolean(false);

    public Consumer(FifoQueue<Command<User>> queue, Repository<User> repository) {
        this.queue = queue;
        this.repository = repository;
    }

    public Command<User> consume() {
        try {
            return queue.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void terminate() {
        this.isTerminated.set(true);
    }
    @Override
    public void run() {
        do {
            Command<User> command = consume();
            if (command != null) {
                command.execute(repository);
            } else {
                Thread.yield();
            }
        } while (!queue.isEmpty() || !isTerminated.get());
        System.out.println("Consumer completing...");
    }
}
