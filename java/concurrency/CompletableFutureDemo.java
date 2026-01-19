package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CompletableFutureDemo {

    public static void completeFutureExample() {
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Hello");
        System.out.println("Already Completed: " + completableFuture.join());
    }

    // Run async (Returns nothing)
    public static void runAsyncTask() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Running in: " + Thread.currentThread().getName());
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Task Completed");
        });

        future.join();
        System.out.println("Main Thread: " + Thread.currentThread().getName());
    }

    // Supply async (Returns a value)
    public static void runSupplyAsyncTask() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching data in: " + Thread.currentThread().getName());
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Data fetched";
        });

        String result = future.join();
        System.out.println("Result: " + result);
        System.out.println("Main Thread: " + Thread.currentThread().getName());
    }

    public static void runWithCustomExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executing in: " + Thread.currentThread().getName());
            return "Task Completed";
        }, executor);
    }

    public static void main(String[] args) {
        completeFutureExample();
        runAsyncTask();
        runSupplyAsyncTask();
        runWithCustomExecutor();
    }
}
