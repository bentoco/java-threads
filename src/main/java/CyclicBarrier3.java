import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class CyclicBarrier3 {

    private static ExecutorService executor;
    private static Runnable r1;
    private static Runnable r2;
    private static Runnable r3;
    private static final BlockingQueue<Double> results = new LinkedBlockingQueue<>();

    // (432*3) + (4^140) + (45*127/12)
    public static void main(String[] args) throws InterruptedException {
        Runnable summarization = () -> {
            double total = 0;
            total += results.poll();
            total += results.poll();
            total += results.poll();
            System.out.println("Result: " + total);
            System.out.println("-----------");
            restart();
        };

        // 3 participant threads
        CyclicBarrier cb = new CyclicBarrier(3, summarization);
        executor = Executors.newFixedThreadPool(3);

        r1 = () -> {
            results.add(432d * 3);
            await(cb);
        };

        r2 = () -> {
            results.add(Math.pow(4, 140));
            await(cb);
        };

        r3 = () -> {
            results.add(45d * 127d / 12d);
            await(cb);
        };

        restart();
        // executor.shutdown();
    }

    private static void restart() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
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
