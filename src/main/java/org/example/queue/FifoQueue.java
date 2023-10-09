package org.example.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class FifoQueue<T> {
    private final BlockingQueue<T> queue;

    public FifoQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    FifoQueue(int capacity) {
        queue = new LinkedBlockingQueue<>(capacity);
    }

    public void write(T u) {
        boolean writeSuccess;
        do {
            // BlockingQueue#offer in a synchronize block to avoid deadlock
            // in case of capacity restrictions
            synchronized (this) {
                writeSuccess = queue.offer(u);
            }
            if (!writeSuccess) {
                Thread.yield();
            }
        } while (!writeSuccess);
    }
    public synchronized T read() throws InterruptedException {
        return queue.poll(50, TimeUnit.MILLISECONDS);
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
