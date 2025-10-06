package com.andreidodu.virtualthreads;

import com.andreidodu.virtualthreads.task.TaskRunnable;

import java.time.Duration;

public class MainYield {

    public static void main(String[] args) throws InterruptedException {
        var t1 = Thread.ofVirtual().name("t1").unstarted(new TaskRunnable(1));
        var t2 = Thread.ofVirtual().name("t2").unstarted(new TaskRunnable(2));

        t1.start();
        t2.start();
        Thread.sleep(Duration.ofSeconds(Integer.MAX_VALUE));
    }
}
