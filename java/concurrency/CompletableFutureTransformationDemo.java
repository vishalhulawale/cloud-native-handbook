package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CompletableFutureTransformationDemo {

    // thenApply transforms result
    public static void thenApplyExample() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "100";
        });

        CompletableFuture<String> transformed = future
                .thenApply(Integer::parseInt)
                .thenApply(num -> num * 2)
                .thenApply(num -> num + " transformed")
                .thenApply(String::toUpperCase);

        System.out.println("Final Result: " + transformed.join());
    }

    public static void thenAcceptExample() {
        CompletableFuture.supplyAsync(() -> {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "100";
                })
                .thenAccept(System.out::println)
                .thenRun(() -> {
                    System.out.println("Running cleanup");
                }).join();
    }

    public static void main(String[] args) {
        thenApplyExample();
        thenAcceptExample();
    }
}
