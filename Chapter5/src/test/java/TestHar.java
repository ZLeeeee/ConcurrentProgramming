import latch.TestHarness;

public class TestHar {
    public static void main(String[] args) {
        TestHarness testHarness = new TestHarness();
        long l = testHarness.timeTask(100, () -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
        });
        System.out.println(l);
    }
}
