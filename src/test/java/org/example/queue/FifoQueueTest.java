package org.example.queue;

import org.junit.jupiter.api.Assertions;

import java.time.Duration;

public class FifoQueueTest {
    @org.junit.jupiter.api.Test
    public void testFifoSingleThread() throws InterruptedException {
        FifoQueue<Integer> fifoQueue = new FifoQueue<>();
        fifoQueue.write(1);
        fifoQueue.write(2);
        fifoQueue.write(3);
        fifoQueue.write(7);
        fifoQueue.write(6);
        fifoQueue.write(5);
        Assertions.assertEquals(1, fifoQueue.read().intValue());
        Assertions.assertEquals(2, fifoQueue.read().intValue());
        Assertions.assertEquals(3, fifoQueue.read().intValue());
        Assertions.assertEquals(7, fifoQueue.read().intValue());
        Assertions.assertEquals(6, fifoQueue.read().intValue());
        Assertions.assertEquals(5, fifoQueue.read().intValue());
        Assertions.assertTrue(fifoQueue.isEmpty());
        Assertions.assertNull(fifoQueue.read());
        fifoQueue.write(2);
        Assertions.assertFalse(fifoQueue.isEmpty());
        fifoQueue.write(2);
        Assertions.assertEquals(2, fifoQueue.read().intValue());
        Assertions.assertEquals(2, fifoQueue.read().intValue());
    }


    @org.junit.jupiter.api.Test
    public void testDoesNotExceedCapacity() {
        FifoQueue<Integer> fifoQueue = new FifoQueue<>(1);
        fifoQueue.write(1);

        org.opentest4j.AssertionFailedError timeoutError = Assertions.assertThrows(org.opentest4j.AssertionFailedError.class,
                () -> Assertions.assertTimeoutPreemptively(
                        Duration.ofMillis(1000), () -> fifoQueue.write(1)
                )
        );
        Assertions.assertEquals("execution timed out after 1000 ms", timeoutError.getMessage());
    }

}
