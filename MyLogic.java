package my;

import java.util.ArrayList;
import java.util.List;

public class MyLogic {

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static String checkAccess(int age) {
        // Логика из примера пользователя: > 18
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    public static String getGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else if (score >= 41 && score <= 60) {
            return "C";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 81 && score <= 100) {
            return "A";
        } else {
            return "Error";
        }
    }

    // Дополнительные методы для выполнения требования "12 тестовых методов"
    // Использую методы из примеров 

    public static String blastOff(int start) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i >= 1; i--) {
            result.append(i);
            if (i > 1) {
                result.append(" ");
            }
        }
        result.append("Поехали!");
        return result.toString();
    }

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static boolean hasBug(String[] messages) {
        if (messages == null) {
            return false;
        }
        for (String message : messages) {
            if (message != null && message.toLowerCase().contains("bug")) {
                return true;
            }
        }
        return false;
    }

    public static String getEvenInRange(int start, int end) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(i);
            }
        }
        return result.toString();
    }

    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым или null");
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static String[] reverse(String[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        String[] reversedArr = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversedArr[arr.length - 1 - i] = arr[i];
        }
        return reversedArr;
    }

    // Метод для параметризованного теста 
    public static boolean isPositive(int n) {
        return n >= 0;
    }
}
