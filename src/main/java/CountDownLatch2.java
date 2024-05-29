import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownLatch2 {

    private static volatile int i = 0;
    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int r = i * j;
            System.out.println(i + " * " + j + " = " + r);
            countDownLatch.countDown();
        };

        Runnable r2 = () -> {
            await();
            i = new Random().nextInt(1000);
        };

        Runnable r3 = () -> {
            await();
            countDownLatch = new CountDownLatch(3);
        };

        Runnable r4 = () -> {
            await();
            System.out.println("Completed!");
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r2, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r3, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r4, 0, 1, TimeUnit.SECONDS);
    }

    private static void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
