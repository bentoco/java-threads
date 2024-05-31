import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ReentrantReadWriteLock {

    private static int i = -1;
    private static final ReadWriteLock lock = new java.util.concurrent.locks.ReentrantReadWriteLock();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        // avoid concurrency without parallelism
        Runnable r1 = () -> {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            String name = Thread.currentThread().getName();
            System.out.println("Writing " + name + ": " + i);
            i++;
            System.out.println("Wrote " + name + ": " + i);
            writeLock.unlock();
        };

        // enhance performance with parallelism
        Runnable r2 = () -> {
            Lock readLock = lock.readLock();
            readLock.lock();
            System.out.println("Reading: " + i);
            System.out.println("Read: " + i);
            readLock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executor.submit(r1);
            executor.submit(r2);
        }

        executor.shutdown();
    }
}
