package com.andreidodu.virtualthreads.task;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public record TaskRunnable(int id, CountDownLatch countDownLatch) implements Runnable {

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            System.out.println("task " + id + "on thread " + Thread.currentThread().getName());
            Thread.yield();
        });
        countDownLatch.countDown();
    }
}
