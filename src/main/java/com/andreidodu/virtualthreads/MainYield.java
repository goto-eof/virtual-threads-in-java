package com.andreidodu.virtualthreads;

import com.andreidodu.virtualthreads.task.TaskRunnable;

import java.util.concurrent.CountDownLatch;

public class MainYield {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread.Builder.OfVirtual builder = Thread.ofVirtual();
        var t1 = builder.name("t1").unstarted(new TaskRunnable(1, countDownLatch));
        var t2 = builder.name("t2").unstarted(new TaskRunnable(2, countDownLatch));

        t1.start();
        t2.start();
        countDownLatch.await();
    }
}
