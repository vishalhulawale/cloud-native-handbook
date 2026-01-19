import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.*;

@FunctionalInterface
interface StringTransformer {
    String transform(String input);
}

public class FunctionalInterfaceDemo {

    public static void main(String[] args) {
        StringTransformer toUpperCase = String::toUpperCase;
        StringTransformer addExclamation = input -> input + "!";

        String original = "hello world";
        String upperCased = toUpperCase.transform(original);
        String exclaimed = addExclamation.transform(original);

        System.out.println("Original: " + original);
        System.out.println("Upper Cased: " + upperCased);
        System.out.println("Exclaimed: " + exclaimed);

        // Predicate
        Predicate<Integer> isEven = number -> number % 2 == 0;
        int testNumber = 4;
        System.out.println("Is " + testNumber + " even? " + isEven.test(testNumber));

        // Bi-Predicate
        BiPredicate<String, Integer> isLongerThan = (str, length) -> str.length() > length;
        String testString = "Functional Interface";
        int lengthToCompare = 10;
        System.out.println("Is \"" + testString + "\" longer than " + lengthToCompare + "? " + isLongerThan.test(testString, lengthToCompare));

        // Function
        Function<String, Integer> vowelCounter = str -> {
            int count = 0;
            for (char c : str.toLowerCase().toCharArray()) {
                if ("aeiou".indexOf(c) != -1) {
                    count++;
                }
            }
            return count;
        };
        String vowelTestString = "Hello Functional Interfaces";
        System.out.println("Number of vowels in \"" + vowelTestString + "\": " + vowelCounter.apply(vowelTestString));

        // Bi-Function
        BiFunction<String, String, String> concatWithSpace = (str1, str2) -> str1 + " " + str2;
        String firstPart = "Hello";
        String secondPart = "World";
        System.out.println("Concatenated String: " + concatWithSpace.apply(firstPart, secondPart));

        // Unary Operator
        UnaryOperator<Integer> square = x -> x * x;
        int numberToSquare = 5;
        System.out.println("Square of " + numberToSquare + ": " + square.apply(numberToSquare));

        // Binary Operator
        BinaryOperator<Integer> sum = Integer::sum;
        int a = 10, b = 20;
        System.out.println("Sum of " + a + " and " + b + ": " + sum.apply(a, b));

        // Consumer
        Consumer<String> printString = System.out::println;
        printString.accept("This is a message from Consumer!");

        // Bi-Consumer
        BiConsumer<String, Integer> printNameAndAge = (name, age) ->
                System.out.println("Name: " + name + ", Age: " + age);
        printNameAndAge.accept("Alice", 30);

        // Supplier
        Supplier<Double> randomValueSupplier = Math::random;
        System.out.println("Random Value: " + randomValueSupplier.get());

        // Runnable
        Runnable runnableTask = () -> {
            System.out.println("Runnable task is running in thread: " + Thread.currentThread().getName());
        };
        Thread thread = new Thread(runnableTask);
        thread.start();

        // Callable
        Callable<String> callableTask = () -> "Callable task result";
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<String> result = executor.submit(callableTask);
            System.out.println("Result: " + result.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
