package com.andreidodu.virtualthreads;

import com.andreidodu.virtualthreads.task.HeavyTaskRunnable;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Main {
    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 5_000_000;

    public static void main(String[] args) throws InterruptedException {
        startVirtualThreads();
        startPlatformThreads();
    }

    private static void startPlatformThreads() throws InterruptedException {
        Thread.Builder.OfPlatform threadBuilder = Thread.ofPlatform().name("platform-thread-");
        startThreads(threadBuilder, MAX_PLATFORM);
    }

    private static void startVirtualThreads() throws InterruptedException {
        Thread.Builder.OfVirtual threadBuilder = Thread.ofVirtual().name("virtual-thread-");
        startThreads(threadBuilder, MAX_VIRTUAL);
    }

    private static void startThreads(final Thread.Builder threadBuilder, final int maxThreads) throws InterruptedException {
        var countDownLatch = new CountDownLatch(maxThreads);

        IntStream.range(0, maxThreads)
                .forEach(i -> {
                    Thread thread = threadBuilder.unstarted(new HeavyTaskRunnable(i, countDownLatch));
                    thread.start();
                });

        countDownLatch.await();
    }
}
