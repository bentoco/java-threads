import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreAcquire {

    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int i = new Random().nextInt(1000);

            acquire(); // permits 3 executions and stop until release()
            System.out.println(name + " result: " + i);
            release(); // await sleep and releases a permit
        };

        for(int j = 0; j < 500; j++) {
            cachedThreadPool.execute(r1);
        }

        cachedThreadPool.shutdown();
    }

    private static void acquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
