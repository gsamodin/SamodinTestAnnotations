import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyStreams {
    public static List<String> top3SeniorEngineers(List<Employee> employees) {
        return employees.stream()
                        .filter(e -> "Инженер".equals(e.title))
                        .sorted((e1, e2) -> Integer.compare(e2.age, e1.age)) // сортировка по убыванию возраста
                        .limit(3)
                        .map(e -> e.name)
                        .collect(Collectors.toList());
    }

    public static double averageEngineerAge(List<Employee> employees) {
        return employees.stream()
                        .filter(e -> "Инженер".equals(e.title))
                        .mapToInt(e -> e.age)
                        .average()
                        .orElseThrow(() -> new RuntimeException("No engineers found"));
    }

    public static String longestWord(List<String> words) {
        return words.stream()
                    .max(Comparator.comparingInt(String::length))
                    .orElseThrow(()-> new IllegalArgumentException("Список слов пуст"));
    }

    public static Map<String, Long> wordFrequency(String text) {
        return Arrays.stream(text.toLowerCase().split("\\s+"))
                     .collect(Collectors.groupingBy(
                             word -> word,
                             Collectors.counting()
                     ));
    }


}
