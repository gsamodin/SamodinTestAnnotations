import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyComparator {
    public static void sortByLengthAndNatural(List<String> words) {
        words.sort(Comparator
                .comparingInt(String::length)    // Сначала по длине слова
                .thenComparing(Comparator.naturalOrder()) // Затем в алфавитном порядке
        );
    }
}
