import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTryAcquire {

    private static final Semaphore semaphore = new Semaphore(100);
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(501);

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int i = new Random().nextInt(1000);

            boolean isAvailable = false;
            counter.incrementAndGet();
            while (!isAvailable) {
                isAvailable = tryAcquire();
            }
            counter.decrementAndGet();
            System.out.println(name + " result: " + i);
            release(); // await sleep and releases a permit
        };

        Window.Message window = Window.create("Counter");
        Runnable r2 = () -> {
            window.setText("Remains: " + counter.get());
        };

        for (int j = 0; j < 500; j++) {
            scheduledExecutorService.execute(r1);
        }
        scheduledExecutorService.scheduleWithFixedDelay(r2, 0, 100, TimeUnit.MICROSECONDS);
    }

    private static boolean tryAcquire() {
        try {
            return semaphore.tryAcquire();
        } catch (Exception e) {
            return false;
        }
    }

    private static void release() {
        sleep();
        semaphore.release();
    }

    // 1 to 6 seconds
    private static void sleep() {
        try {
            int waitingTime = new Random().nextInt(6);
            waitingTime++;
            Thread.sleep(1000 * waitingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
