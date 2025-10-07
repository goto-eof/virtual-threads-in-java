package com.andreidodu.virtualthreads.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

public class AutoClosableConcurrentLimiterExecutorService implements AutoCloseable {

    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public AutoClosableConcurrentLimiterExecutorService(ExecutorService executorService, int numbConcurrentThreads) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(numbConcurrentThreads);
    }

    public void execute(Runnable runnable) {

        executorService.execute(() -> {
            try {
                this.semaphore.acquire();
                runnable.run();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                this.semaphore.release();
            }
        });

    }

    @Override
    public void close() throws Exception {
        this.executorService.close();
    }
}
