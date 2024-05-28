import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrier1 {

    // (432*3) + (4^140) + (45*127/12)
    public static void main(String[] args) {

        // 3 participant threads
        CyclicBarrier cb = new CyclicBarrier(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            System.out.println(432d*3);
            await(cb);
            System.out.println("Finished");
        };

        Runnable r2 = () -> {
            System.out.println(Math.pow(4, 140));
            await(cb);
            System.out.println("Finished");
        };

        Runnable r3 = () -> {
            System.out.println(45d*127d/12d);
            await(cb);
            System.out.println("Finished");
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
