import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SynchronizedQueue {

    /**
     * Used to exchange data between threads, in the example we insert and fetch a value from the queue,
     * if the value is not fetched the operation is stopped until it is carried out.
     * <p>
     * To test the behaviour, comment out the get snippet and execute.
     * <p>
     * Note: you can set a timeout
     **/

    private static final SynchronousQueue<String> queue = new SynchronousQueue<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            put();
            System.out.println("wrote in the queue");
        };

        Runnable r2 = () -> {
            String result = get();
            System.out.println("gotcha: " + result);
        };

        executorService.submit(r1);
        executorService.submit(r2);

        executorService.shutdown();
    }

    private static void put() {
        try {
            queue.put("goodbye, world!");
//            queue.offer("goodbye, world!", 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static String get() {
        try {
            return queue.take();
//            return queue.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return null;
        }
    }
}
