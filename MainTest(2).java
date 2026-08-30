package my;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class MainTest {

    private static final Random random = new Random();

    // Задача 1: Обязательные 3 метода, добавляю асерты

    // 1. возвращает булево значение. Ассерт проверяет равенство.
    @Test
    @Tag("basic")
    void testIsEven() {
        System.out.println("========================Test method start");
        int n = random.nextInt(100) + 1;
        boolean result = MyLogic.isEven(n);
        boolean expected = (n % 2 == 0);

        Assertions.assertEquals(expected, result,
                "Для числа " + n + " ожидалось четность: " + expected + ", но получено: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 2. checkAccess: возвращает строку. Ассерт проверяет равенство строк.
    @RepeatedTest(20)
    @Tag("basic")
    void testCheckAccess() {
        System.out.println("========================Test method start");
        int age = random.nextInt(100);
        String result = MyLogic.checkAccess(age);
        String expected = (age > 18) ? "Allowed" : "Denied";

        Assertions.assertEquals(expected, result,
                "Для возраста " + age + " ожидался доступ: " + expected + ", но получено: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 3. getGrade: параметризованный тест
    static Stream<Arguments> provideScoresForGetGrade() {
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            scores.add(random.nextInt(106) - 3); // Числа от -3 до 102
        }
        return scores.stream().map(Arguments::of);
    }

    @ParameterizedTest(name = "Тест оценки для балла {0}")
    @MethodSource("provideScoresForGetGrade")
    @Tag("grade")
    void testGetGrade(int score) {
        System.out.println("========================Test method start");
        String grade = MyLogic.getGrade(score);

        String expected;
        if (score >= 0 && score <= 20) expected = "E";
        else if (score >= 21 && score <= 40) expected = "D";
        else if (score >= 41 && score <= 60) expected = "C";
        else if (score >= 61 && score <= 80) expected = "B";
        else if (score >= 81 && score <= 100) expected = "A";
        else expected = "Error";

        Assertions.assertEquals(expected, grade,
                "Для балла " + score + " ожидалась оценка: " + expected + ", но получена: " + grade);

        System.out.println("Test method end");
        System.out.println("========================");
    }

    // Задача 2: Остальные тесты с асертами и тегами

    // 4. blastOff
    @Test
    @Tag("string")
    void testBlastOff() {
        System.out.println("========================Test method start");
        int start = random.nextInt(10) + 1;
        String result = MyLogic.blastOff(start);

        StringBuilder expectedBuilder = new StringBuilder();
        for (int i = start; i >= 1; i--) {
            expectedBuilder.append(i);
            if (i > 1) expectedBuilder.append(" ");
        }
        expectedBuilder.append("Поехали!");
        String expected = expectedBuilder.toString();

        Assertions.assertEquals(expected, result,
                "Для старта " + start + " ожидалась строка: " + expected + ", но получена: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 5. sumToN
    @RepeatedTest(5)
    @Tag("math")
    void testSumToN() {
        System.out.println("========================Test method start");
        int n = random.nextInt(100) + 1;
        int result = MyLogic.sumToN(n);
        int expected = n * (n + 1) / 2;

        Assertions.assertEquals(expected, result,
                "Сумма до " + n + " ожидалась: " + expected + ", но получена: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 6. hasBug
    @ParameterizedTest
    @ValueSource(strings = {"Hello Bug World", "no bugs here", "BUG in code"})
    @Tag("logic")
    void testHasBug(String input) {
        System.out.println("========================Test method start");
        String[] arr = {input};
        boolean result = MyLogic.hasBug(arr);
        boolean expected = input.toLowerCase().contains("bug");

        Assertions.assertEquals(expected, result,
                "Для сообщения '" + input + "' ожидалось наличие бага: " + expected + ", но получено: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 7. getEvenInRange
    @Test
    @Tag("string")
    void testGetEvenInRange() {
        System.out.println("========================Test method start");
        int start = random.nextInt(10);
        int end = start + random.nextInt(10) + 1;

        String result = MyLogic.getEvenInRange(start, end);

        StringBuilder expectedBuilder = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (expectedBuilder.length() > 0) expectedBuilder.append(" ");
                expectedBuilder.append(i);
            }
        }
        String expected = expectedBuilder.toString();

        Assertions.assertEquals(expected, result,
                "Для диапазона [" + start + ", " + end + "] ожидалось: " + expected + ", но получено: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 8. findMax
    @RepeatedTest(3)
    @Tag("math")
    void testFindMax() {
        System.out.println("========================Test method start");
        int[] arr = new int[5];
        for(int i=0; i<5; i++) arr[i] = random.nextInt(100);

        int result = MyLogic.findMax(arr);

        int expected = arr[0];
        for(int val : arr) if(val > expected) expected = val;

        Assertions.assertEquals(expected, result,
                "Максимум в массиве ожидался: " + expected + ", но получено: " + result);

        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 9. reverse
    @Test
    @Tag("array")
    void testReverse() {
        System.out.println("========================Test method start");
        String[] input = {"A", "B", "C", "D"};
        String[] result = MyLogic.reverse(input);

        String[] expected = {"D", "C", "B", "A"};

        Assertions.assertArrayEquals(expected, result,
                "Реверс массива ожидался: " + Arrays.toString(expected) + ", но получено: " + Arrays.toString(result));

        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 10. isPositive
    @RepeatedTest(2)
    @Tag("logic")
    void testIsPositive() {
        System.out.println("========================Test method start");
        int n = random.nextInt(200) - 100;
        boolean result = MyLogic.isPositive(n);
        boolean expected = n >= 0;

        Assertions.assertEquals(expected, result,
                "Для числа " + n + " ожидалось положительное: " + expected + ", но получено: " + result);

        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 11. calcAverage (добавлен метод в MyLogic)
    @Test
    @Tag("math")
    void testCalcAverage() {
        System.out.println("========================Test method start");
        List<Integer> list = Arrays.asList(10, 20, 30, 40);
        double result = MyLogic.calcAverage(list);
        double expected = 25.0;

        Assertions.assertEquals(expected, result, 0.0001,
                "Среднее арифметическое ожидалось: " + expected + ", но получено: " + result);
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 12. removeSpecificName (добавлен метод в MyLogic)
    @Test
    @Tag("list")
    void testRemoveSpecificName() {
        System.out.println("========================Test method start");
        List<String> names = Arrays.asList("Роман", "Серёга", "Ира", "Лев", "Аня");
        List<String> result = MyLogic.removeSpecificName(names, "Лев");

        // Проверяем, что "Лев" удален
        Assertions.assertTrue(!result.contains("Лев"),
                "Список не должен содержать 'Лев', но содержит: " + result);
        Assertions.assertEquals(4, result.size(),
                "Размер списка ожидался 4, но получено: " + result.size());
        System.out.println("Test method end");
        System.out.println("========================");
    }
}
