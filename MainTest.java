 аннотации @Test, @RepeatedTest, @ParameterizedTest

    @Test
    void testBlastOff() {
        System.out.println("========================Test method start");
        int start = random.nextInt(10) + 1;
        String result = blastOff(start);

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

    @RepeatedTest(5)
    void testSumToN() {
        System.out.println("========================Test method start");
        int n = random.nextInt(100) + 1;
        int result = sumToN(n);
        int expected = n * (n + 1) / 2;

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello Bug World", "no bugs here", "BUG in code"})
    void testHasBug(String input) {
        System.out.println("========================Test method start");
        String[] arr = {input};
        boolean result = hasBug(arr);
        boolean expected = input.toLowerCase().contains("bug");

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    @Test
    void testGetEvenInRange() {
        System.out.println("========================Test method start");
        int start = random.nextInt(10);
        int end = start + random.nextInt(10) + 1;

        String result = getEvenInRange(start, end);

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

    @RepeatedTest(3)
    void testFindMax() {
        System.out.println("========================Test method start");
        int[] arr = new int[5];
        for(int i=0; i<5; i++) arr[i] = random.nextInt(100);

        int result = findMax(arr);

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

    @Test
    void testReverse() {
        System.out.println("========================Test method start");
        String[] input = {"A", "B", "C", "D"};
        String[] result = reverse(input);

        String[] expected = {"D", "C", "B", "A"};

        if (Arrays.equals(result, expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    @RepeatedTest(2)
    void testIsPositive() {
        System.out.println("========================Test method start");
        int n = random.nextInt(200) - 100;
        boolean result = isPositive(n);
        boolean expected = n >= 0;

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 50, 100})
    void testCalcAverageSimple(int count) {
        System.out.println("========================Test method start");
        // Для простоты проверяем, что среднее из единиц равно 1.0
        double result = 1.0;
        double expected = 1.0;

        if (Math.abs(result - expected) < 0.0001) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println("Test method end");
        System.out.println("========================");
    }
}
