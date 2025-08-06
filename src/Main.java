import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //TestRunner.runTests(TestClass.class);
        int [] numbers = {5, 2, 10, 9, 4, 3, 10, 1, 13};
        System.out.println("3-е наибольшее число: " + MyPriorityQueue.thirdMax(numbers)); // 10 ;
        System.out.println("3-е наибольшее уникальное число: " + MyPriorityQueue.thirdDistinctMax(numbers)); // 9

        List<Employee> employees = Arrays.asList(
                new Employee("Анна", 35, "Инженер"),
                new Employee("Борис", 42, "Инженер"),
                new Employee("Виктор", 30, "Дизайнер"),
                new Employee("Галина", 45, "Инженер"),
                new Employee("Дмитрий", 50, "Инженер"),
                new Employee("Елена", 28, "Инженер")
        );

        List<String> result = MyStreams.top3SeniorEngineers(employees);
        System.out.println("Три самых старших инженера: " + result);

        System.out.println("Средний возраст инженеров:" + MyStreams.averageEngineerAge(employees));

        List<String> words = Arrays.asList(
                "Java", "Python", "JavaScript", "C++", "Kotlin", "TypeScript" , "Гладиолус"
        );

        String longest = MyStreams.longestWord(words);
        System.out.println("Самое длинное слово: " + longest);

        String text = "Тридцать три корабля лавировали, лавировали, лавировали, лавировали да не вылавировали";
        Map<String, Long> frequencyMap = MyStreams.wordFrequency(text);

        System.out.println("Частота слов:");
        frequencyMap.forEach((word, count) ->
                System.out.printf("'%s': %d раз(а)\n", word, count));


        words = Arrays.asList(
                "Яблоко", "Груша", "Апельсин", "Лимон", "Киви", "Виноград"
        );

        MyComparator.sortByLengthAndNatural(words);
        System.out.println("Строки по возрастанию длины слова, при равной длине — в алфавитном порядке:");
        words.forEach(System.out::println);


        String[] wordsList = {"apple banana orange grape strawberry",
                "the quick brown fox jumps",
                "jumps over the lazy dog"};

        List<String> allWords = new ArrayList<>();

        for (String s : wordsList) {
            // Разделяем строку на слова и проверяем, что их ровно 5
            String[] wordArray = s.split("\\s+");
            if (wordArray.length == 5) {
                Collections.addAll(allWords, wordArray);
            }
        }

        System.out.println("Самое длинное слово из массива строк: " + MyStreams.longestWord(allWords));

    }
}