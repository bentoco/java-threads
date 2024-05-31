import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

public class ReentrantLock {

    private static int i = -1;

    public static void main(String[] args) {
        Lock lock = new java.util.concurrent.locks.ReentrantLock();
        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            lock.lock();
//            boolean b = lock.tryLock();
            String name = Thread.currentThread().getName();
            System.out.println("Writing " + name + ": " + i);
            i++;
            System.out.println("Wrote " + name + ": " + i);
            lock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executor.submit(r1);
        }

        executor.shutdown();
    }
}
