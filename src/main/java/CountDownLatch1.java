import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownLatch1 {

    private static volatile int i = 0;
    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int r = i * j;
            System.out.println(i + " * " + j + " = " + r);
            countDownLatch.countDown();
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        while (true) {
            sleep();
            i = new Random().nextInt(1000);
            countDownLatch = new CountDownLatch(3);
        }
    }

    private static void sleep() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
