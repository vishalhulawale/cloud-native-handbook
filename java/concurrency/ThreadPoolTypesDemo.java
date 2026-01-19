package concurrency;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class ThreadPoolTypesDemo {

    public static void createAndUseSingleThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> getFirstName = () -> {
            System.out.println("\nExecuted by: " + Thread.currentThread().getName());
            sleep(2000);
            return "Vishal";
        };

        Callable<String> getLastName = () -> {
            System.out.println("\nExecuted by: " + Thread.currentThread().getName());
            sleep(2000);
            return "Vishal";
        };

        Future<String> firstName = executorService.submit(getFirstName);
        System.out.println("FirstName:" + firstName.get());

        Future<String> lastName = executorService.submit(getLastName);
        System.out.println("LastName:" + lastName.get());

        executorService.shutdown();
    }


    public static void createAndUseFixedThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> getFirstName = () -> {
            System.out.println("\nExecuted by: " + Thread.currentThread().getName());
            sleep(2000);
            return "Vishal";
        };

        Callable<String> getLastName = () -> {
            System.out.println("\nExecuted by: " + Thread.currentThread().getName());
            sleep(2000);
            return "Vishal";
        };

        Future<String> firstName = executorService.submit(getFirstName);
        System.out.println("FirstName:" + firstName.get());

        Future<String> lastName = executorService.submit(getLastName);
        System.out.println("LastName:" + lastName.get());

        executorService.shutdown();
    }

    public static void createAndUseScheduledThreadPool() throws ExecutionException, InterruptedException {
        ScheduledExecutorService  executorService = Executors.newScheduledThreadPool(5);

        Callable<String> getFirstName = () -> {
            System.out.println("\nExecuted by: " + Thread.currentThread().getName());
            sleep(2000);
            return "Vishal";
        };

        Runnable getLastName = () -> {
            System.out.println("\nExecuted by: " + Thread.currentThread().getName());
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // Run once after delay
        Future<String> firstName = executorService.schedule(getFirstName, 3 , TimeUnit.SECONDS);
        System.out.println("FirstName:" + firstName.get());

        executorService.scheduleAtFixedRate(getLastName, 0, 5, TimeUnit.SECONDS);
//        executorService.shutdown();
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        createAndUseSingleThreadPool();
        createAndUseFixedThreadPool();
        createAndUseScheduledThreadPool();
    }
}
