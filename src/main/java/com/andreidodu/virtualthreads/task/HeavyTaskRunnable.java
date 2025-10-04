package com.andreidodu.virtualthreads.task;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public record HeavyTaskRunnable(int id, CountDownLatch countDownLatch) implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Heavy task " + id + " started");
            Thread.sleep(Duration.ofSeconds(10));
            countDownLatch.countDown();
            System.out.println("Heavy task " + id + " finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
