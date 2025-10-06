package com.andreidodu.virtualthreads;

import com.andreidodu.virtualthreads.task.TaskRunnable;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class MainYield {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        var t1 = Thread.ofVirtual().name("t1").unstarted(new TaskRunnable(1, countDownLatch));
        var t2 = Thread.ofVirtual().name("t2").unstarted(new TaskRunnable(2, countDownLatch));

        t1.start();
        t2.start();
        countDownLatch.await();
    }
}
