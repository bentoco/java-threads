import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class CyclicBarrier2 {

    private static final BlockingQueue<Double> results = new LinkedBlockingQueue<>();

    // (432*3) + (4^140) + (45*127/12)
    public static void main(String[] args) {
        Runnable conclude = () -> {
            double total = 0;
            total += results.poll();
            total += results.poll();
            total += results.poll();
            System.out.println("Result: " + total);
        };

        // 3 participant threads
        CyclicBarrier cb = new CyclicBarrier(3, conclude);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(432d * 3);
            await(cb);
            System.out.println(Thread.currentThread().getName());
        };

        Runnable r2 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(Math.pow(4, 140));
            await(cb);
            System.out.println(Thread.currentThread().getName());
        };

        Runnable r3 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(45d * 127d / 12d);
            await(cb);
            System.out.println(Thread.currentThread().getName());
        };

        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);

        executor.shutdown();
    }

    // wait until all the participants complete
    private static void await(CyclicBarrier cb) {
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

}
