public class TestClass {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("BeforeSuite - preparing test environment");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("AfterSuite - cleaning up test environment");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest - preparing for a test");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest - cleaning up after a test");
    }

    @Test(priority = 1)
    public void highPriorityTest() {
        System.out.println("Running high priority test (priority=1)");
    }

    @Test(priority = 5)
    public void defaultPriorityTest() {
        System.out.println("Running default priority test (priority=5)");
    }

    @Test(priority = 10)
    public void lowPriorityTest() {
        System.out.println("Running low priority test (priority=10)");
    }

    @Test
    @CsvSource("42, Hello, 3.14, true")
    public void parameterizedTest(int number, String text, double pi, boolean flag) {
        System.out.printf("Parameterized test: number=%d, text='%s', pi=%.2f, flag=%b%n",
                number, text, pi, flag);
    }

    @Test(priority = 3)
    public void anotherTest() {
        System.out.println("Running another test with priority 3");
    }
}
