package my;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class MyLogic {

    // 1. Проверка на четность
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    // 2. Проверка доступа по возрасту
    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    // 3. Получение оценки по баллу
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

    // 4. Обратный отсчет
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

    // 5. Сумма до N
    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    // 6. Поиск бага в сообщениях
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

    // 7. Четные числа в диапазоне
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

    // 8. Максимум в массиве
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

    // 9. Реверс массива строк
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

    // 10. Проверка на положительное число
    public static boolean isPositive(int n) {
        return n >= 0;
    }

    // 11. Среднее арифметическое списка
    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        long sum = 0;
        for (Integer number : list) {
            if (number != null) {
                sum += number;
            }
        }
        return (double) sum / list.size();
    }

    // 12. Удаление конкретного имени из списка
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> result = new ArrayList<>();
        if (list == null) {
            return result;
        }
        for (String item : list) {
            boolean shouldRemove = false;
            if (nameToRemove == null) {
                if (item == null) {
                    shouldRemove = true;
                }
            } else {
                if (item != null && item.equals(nameToRemove)) {
                    shouldRemove = true;
                }
            }
            if (!shouldRemove) {
                result.add(item);
            }
        }
        return result;
    }
}
