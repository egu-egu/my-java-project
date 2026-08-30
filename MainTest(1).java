package my;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class MainTest {

    private static final Random random = new Random();

    // Задача 1: Обязательные 3 метода

    // 1. isEven: 1 раз, случайное число 1-100
    @Test
    void testIsEven() {
        System.out.println("========================Test method start");
        int n = random.nextInt(100) + 1;
        boolean result = MyLogic.isEven(n);
        boolean expected = (n % 2 == 0);

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 2. checkAccess: 20 раз, случайные числа 0-99
    @RepeatedTest(20)
    void testCheckAccess() {
        System.out.println("========================Test method start");
        int age = random.nextInt(100);
        String result = MyLogic.checkAccess(age);
        String expected = (age > 18) ? "Allowed" : "Denied";

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
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

        if (grade.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // Задача 2: Остальные тесты для достижения 12 методов

    // 4. blastOff
    @Test
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

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 5. sumToN
    @RepeatedTest(5)
    void testSumToN() {
        System.out.println("========================Test method start");
        int n = random.nextInt(100) + 1;
        int result = MyLogic.sumToN(n);
        int expected = n * (n + 1) / 2;

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 6. hasBug
    @ParameterizedTest
    @ValueSource(strings = {"Hello Bug World", "no bugs here", "BUG in code"})
    void testHasBug(String input) {
        System.out.println("========================Test method start");
        String[] arr = {input};
        boolean result = MyLogic.hasBug(arr);
        boolean expected = input.toLowerCase().contains("bug");

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 7. getEvenInRange
    @Test
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

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 8. findMax
    @RepeatedTest(3)
    void testFindMax() {
        System.out.println("========================Test method start");
        int[] arr = new int[5];
        for(int i=0; i<5; i++) arr[i] = random.nextInt(100);

        int result = MyLogic.findMax(arr);

        int expected = arr[0];
        for(int val : arr) if(val > expected) expected = val;

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 9. reverse
    @Test
    void testReverse() {
        System.out.println("========================Test method start");
        String[] input = {"A", "B", "C", "D"};
        String[] result = MyLogic.reverse(input);

        String[] expected = {"D", "C", "B", "A"};

        if (Arrays.equals(result, expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 10. isPositive
    @RepeatedTest(2)
    void testIsPositive() {
        System.out.println("========================Test method start");
        int n = random.nextInt(200) - 100;
        boolean result = MyLogic.isPositive(n);
        boolean expected = n >= 0;

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 11. calcAverage (добавлен метод в MyLogic)
    @Test
    void testCalcAverage() {
        System.out.println("========================Test method start");
        List<Integer> list = Arrays.asList(10, 20, 30, 40);
        double result = MyLogic.calcAverage(list);
        double expected = 25.0;

        if (Math.abs(result - expected) < 0.0001) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // 12. removeSpecificName (добавлен метод в MyLogic)
    @Test
    void testRemoveSpecificName() {
        System.out.println("========================Test method start");
        List<String> names = Arrays.asList("Роман", "Серёга", "Ира", "Лев", "Аня");
        List<String> result = MyLogic.removeSpecificName(names, "Лев");

        // Проверяем, что "Лев" удален
        boolean passed = !result.contains("Лев") && result.size() == 4;

        if (passed) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }
}
