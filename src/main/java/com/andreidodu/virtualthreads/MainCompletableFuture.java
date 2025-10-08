package com.andreidodu.virtualthreads;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class MainCompletableFuture {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("starting...");

        runAsync()
                .thenRun(() -> System.out.println("runAsync() done!"));

        supplyAsync()
                .thenAccept(System.out::println)
                .thenRun(() -> System.out.println("supplyAsync() done!"));

        complete()
                .thenAccept(System.out::println)
                .thenRun(() -> System.out.println("complete() done!"));

        System.out.println("ending...");
        Thread.sleep(Duration.ofSeconds(3));
    }


    private static CompletableFuture<Void> runAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static CompletableFuture<String> supplyAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
                return "Hello World!";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static CompletableFuture<String> complete() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Thread.ofVirtual().start(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            completableFuture.complete("Hello World!");
        });

        return completableFuture;
    }

}
