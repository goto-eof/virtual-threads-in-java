package com.andreidodu.virtualthreads;

import com.sun.jdi.VoidType;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class MainCompletableFuture {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("starting...");

        runAsync()
                .thenRun(() -> System.out.println("runAsync() done!"));


        CompletableFuture<String> resSupply = supplyAsync()
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return e.getMessage();
                });


        CompletableFuture<String> resultComplete = complete()
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return e.getMessage();
                });

        System.out.println("ending...");
        System.out.printf("result: %s | %s%n", resSupply.join(), resultComplete.join());
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
                throw new RuntimeException("error");
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
