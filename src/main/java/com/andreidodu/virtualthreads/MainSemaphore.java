package com.andreidodu.virtualthreads;

import com.andreidodu.virtualthreads.task.HeavyTaskRunnable;
import com.andreidodu.virtualthreads.util.AutoClosableConcurrentLimiterExecutorService;

import java.util.concurrent.Executors;

public class MainSemaphore {

    public static void main(String[] args) throws Exception {

        var executorService = Executors.newVirtualThreadPerTaskExecutor();

        try (AutoClosableConcurrentLimiterExecutorService limitedExecutorService = new AutoClosableConcurrentLimiterExecutorService(executorService, 3)) {

            for (int i = 0; i < 100; i++) {
                limitedExecutorService.execute(new HeavyTaskRunnable(i));
            }
        }

    }

}
