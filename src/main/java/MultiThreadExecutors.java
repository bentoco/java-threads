import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadExecutors {

    public static void main(String[] args) {
        // ExecutorService executor = Executors.newFixedThreadPool(4);
        ExecutorService executor = Executors.newCachedThreadPool();

        Task t1 = new Task();
        Task t2 = new Task();
        Task t3 = new Task();
        Task t4 = new Task();

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(new Task());
        }

        try {
            /* Future<String> r1 = executor.submit(t1);
            Future<String> r2 = executor.submit(t2);
            Future<String> r3 = executor.submit(t3);
            Future<String> r4 = executor.submit(t4);
            System.out.println(r1.get());
            System.out.println(r2.get());
            System.out.println(r3.get());
            System.out.println(r4.get()); */

            // invoke all
            /*
            List<Future<String>> futures = executor.invokeAll(tasks);
            for (Future<String> f: futures){
                System.out.println(f.get());
            } */

            // invoke any
            String f1 = executor.invokeAny(tasks);
            System.out.println(f1);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdownNow();
        }
    }

    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            int n = new Random().nextInt(1000);
            return (name + " " + n);
        }
    }
}
