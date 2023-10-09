package org.example.queue;

import org.example.command.Command;
import org.example.domain.User;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable {
    private final FifoQueue<Command<User>> queue;
    private final LinkedList<Command<User>> input;

    private final CountDownLatch countDownLatch;

    public Producer(FifoQueue<Command<User>> queue, LinkedList<Command<User>> input, CountDownLatch countDownLatch) {
        this.queue = queue;
        this.input = input;
        this.countDownLatch = countDownLatch;
    }

    public void produce(Command<User> command) {
        if (command!=null) {
            queue.write(command);
        }
    }
    @Override
    public void run() {
        for (Command<User> command : input) {
            try {
                System.out.println("Producer producing...");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            produce(command);
        }
        System.out.println("Producer completing...");
        countDownLatch.countDown();
    }
}
