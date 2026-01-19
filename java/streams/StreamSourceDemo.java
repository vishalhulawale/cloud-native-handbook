package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamSourceDemo {

    public static void main(String[] args) throws IOException {

        // 1. Collection
        List<String> list = List.of("Hello", "World");
        Stream<String> listStream = list.stream();
        System.out.println("List Stream:");
        listStream.forEach(System.out::println);

        // 2. Arrays
        Integer[] numbers = {1, 2, 3, 4, 5, 6};
        Stream<Integer> numberStream = Arrays.stream(numbers);
        System.out.println("Array Stream:");
        numberStream.forEach(System.out::println);

        // 3. I/O Stream
        Stream<String> lineStream = Files.lines(Path.of("C:\\Workspace\\cloud-native-handbook\\java\\streams\\data.txt"));
        System.out.println("File Line Stream:");
        lineStream.forEach(System.out::println);

        // 4. Infinite Stream
        Stream<Double> randomNumStream = Stream.generate(Math::random);
        System.out.println("Random Number Stream:");
        randomNumStream.limit(5).forEach(System.out::println);
    }
}
