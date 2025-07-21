import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class TestRunner {
    public static void runTests(Class<?> testClass) {
        try {
            // Проверка аннотаций BeforeSuite и AfterSuite
            validateSuiteAnnotations(testClass);

            // Создаем экземпляр тестового класса
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            // Выполняем BeforeSuite если есть
            runBeforeSuite(testClass, testInstance);

            // Получаем и сортируем тестовые методы
            List<Method> testMethods = getSortedTestMethods(testClass);

            // Выполняем тесты
            for (Method testMethod : testMethods) {
                runSingleTest(testInstance, testMethod);
            }

            // Выполняем AfterSuite если есть
            runAfterSuite(testClass, testInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validateSuiteAnnotations(Class<?> testClass) {
        long beforeSuiteCount = Arrays.stream(testClass.getDeclaredMethods())
                                      .filter(m -> m.isAnnotationPresent(BeforeSuite.class))
                                      .count();

        if (beforeSuiteCount > 1) {
            throw new RuntimeException("BeforeSuite annotation can be used only once");
        }

        long afterSuiteCount = Arrays.stream(testClass.getDeclaredMethods())
                                     .filter(m -> m.isAnnotationPresent(AfterSuite.class))
                                     .count();

        if (afterSuiteCount > 1) {
            throw new RuntimeException("AfterSuite annotation can be used only once");
        }
    }

    private static void runBeforeSuite(Class<?> testClass, Object testInstance) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("BeforeSuite method must be static");
                }
                method.invoke(null);
            }
        }
    }

    private static void runAfterSuite(Class<?> testClass, Object testInstance) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("AfterSuite method must be static");
                }
                method.invoke(null);
            }
        }
    }

    private static List<Method> getSortedTestMethods(Class<?> testClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                     .filter(m -> m.isAnnotationPresent(Test.class))
                     .sorted(Comparator.comparingInt((Method m) ->
                             m.getAnnotation(Test.class).priority()).reversed())
                     .collect(Collectors.toList());
    }

    private static void runSingleTest(Object testInstance, Method testMethod) throws Exception {
        // Выполняем BeforeTest методы
        runAnnotatedMethods(testInstance, testMethod.getDeclaringClass(), BeforeTest.class);

        try {
            // Проверяем наличие CsvSource
            if (testMethod.isAnnotationPresent(CsvSource.class)) {
                runTestWithCsvSource(testInstance, testMethod);
            } else {
                // Просто вызываем тест без параметров
                if (testMethod.getParameterCount() == 0) {
                    testMethod.invoke(testInstance);
                } else {
                    throw new RuntimeException("Test method requires parameters but no CsvSource provided");
                }
            }
        } finally {
            // Выполняем AfterTest методы
            runAnnotatedMethods(testInstance, testMethod.getDeclaringClass(), AfterTest.class);
        }
    }
    private static void runAnnotatedMethods(Object testInstance, Class<?> testClass,
                                            Class<? extends Annotation> annotation) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                method.invoke(testInstance);
            }
        }
    }

    private static void runTestWithCsvSource(Object testInstance, Method testMethod) throws Exception {
        CsvSource csvSource = testMethod.getAnnotation(CsvSource.class);
        String[] values = csvSource.value().split(",\\s*");

        if (values.length != testMethod.getParameterCount()) {
            throw new RuntimeException("Number of parameters in CsvSource doesn't match method parameters");
        }

        Object[] args = new Object[values.length];
        Class<?>[] parameterTypes = testMethod.getParameterTypes();

        for (int i = 0; i < values.length; i++) {
            args[i] = convertValue(values[i], parameterTypes[i]);
        }

        testMethod.invoke(testInstance, args);
    }

    private static Object convertValue(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == char.class || targetType == Character.class) {
            return value.charAt(0);
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(value);
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(value);
        } else {
            throw new RuntimeException("Unsupported parameter type: " + targetType.getName());
        }
    }
}
