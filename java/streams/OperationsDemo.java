package streams;

import java.util.Comparator;
import java.util.List;

public class OperationsDemo {

    public static void main(String[] args) {

        List<String> colorList1 = List.of("Blue", "Red", "Green", "Yellow", "Purple", "Orange");
        List<String> colorList2 = List.of("Cyan", "Magenta", "Yellow", "Black");

        List<List<String>> nestedColorList = List.of(colorList1, colorList2);

        // Intermediate Operations
        // 1. filter
        colorList1.stream()
                .filter(color -> color.length() > 4)
                .forEach(color -> System.out.println("Filtered Color: " + color));

        // 2. map
        colorList1.stream()
                .map(String::toUpperCase)
                .forEach(color -> System.out.println("Mapped Color: " + color));

        // 3. flatMap
        nestedColorList.stream()
                .flatMap(List::stream)
                .forEach(color -> System.out.println("FlatMapped Color: " + color));

        // 4. Distinct
        List<String> colorsWithDuplicates = List.of("Blue", "Red", "Green", "Blue", "Red");
        colorsWithDuplicates.stream()
                .distinct()
                .forEach(color -> System.out.println("Distinct Color: " + color));

        // 5. Sorted
        colorList1.stream()
                .sorted()
                .forEach(color -> System.out.println("Sorted Color [Natural Order]: " + color));

        colorList1.stream()
                .sorted(Comparator.comparingInt(String::length))
                .forEach(color -> System.out.println("Sorted Color [By Length]: " + color));

        // 6. Peek
        colorList1.stream()
                .peek(color -> System.out.println("Peeked Color: " + color))
                .forEach(color -> {});

        // 7. Limit
        colorList1.stream()
                .limit(3)
                .forEach(color -> System.out.println("Limited Color: " + color));

        // 8. Skip
        colorList1.stream()
                .skip(2)
                .forEach(color -> System.out.println("Skipped Color: " + color));

        // Terminal Operations
        // 1. forEach
        colorList1
                .forEach(color -> System.out.println("ForEach Color: " + color));

        // 2. toArray
        Object[] colorArray = colorList1.toArray();
        System.out.println("Color Array Length: " + colorArray.length);

        // 3. reduce
        String concatenatedColors = colorList1.stream()
                .reduce("", (acc, color) -> acc + color + " ");
        System.out.println("Concatenated Colors: " + concatenatedColors.trim());

        // 4. collect
        List<String> upperCaseColors = colorList1.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println("Collected UpperCase Colors: " + upperCaseColors);

        // 5. count
        long colorCount = colorList1.stream()
                .count();
        System.out.println("Color Count: " + colorCount);

        // 6. min and max
        colorList1.stream()
                .min(Comparator.naturalOrder())
                .ifPresent(minColor -> System.out.println("Min Color: " + minColor));
        colorList1.stream()
                .max(Comparator.naturalOrder())
                .ifPresent(maxColor -> System.out.println("Max Color: " + maxColor));

        // 7. findAny and findFirst
        colorList1.stream()
                .findAny()
                .ifPresent(anyColor -> System.out.println("Find Any Color: " + anyColor));
        colorList1.stream()
                .findFirst()
                .ifPresent(firstColor -> System.out.println("Find First Color: " + firstColor));

        // 8. allMatch, anyMatch, noneMatch
        boolean allMatch = colorList1.stream()
                .allMatch(color -> color.length() > 2);
        System.out.println("All Match Length > 2: " + allMatch);

        boolean anyMatch = colorList1.stream()
                .anyMatch(color -> color.startsWith("R"));
        System.out.println("Any Match Starts with R: " + anyMatch);

        boolean noneMatch = colorList1.stream()
                .noneMatch(color -> color.equals("Black"));
        System.out.println("None Match is Black: " + noneMatch);

        // 9. findFirst and findAny with parallel stream
        colorList1.parallelStream()
                .findFirst()
                .ifPresent(firstColor -> System.out.println("Parallel Stream Find First Color: " + firstColor));
        colorList1.parallelStream()
                .findAny()
                .ifPresent(anyColor -> System.out.println("Parallel Stream Find Any Color: " + anyColor));
    }

}

