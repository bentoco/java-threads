import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SingleThreadRunnableExecutors {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
            Future<?> submitted = executorService.submit(new MyRunnable());

            System.out.println(submitted.isDone());
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(submitted.isDone());
        } catch (Exception e) {
            throw e;
        } finally {
            if (executorService != null)
                executorService.shutdownNow();
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            Thread.State state = Thread.currentThread().getState();
            System.out.printf("Hello from my runnable! T_NAME: %s; T_STATE: %s%n", name, state);
        }
    }
}
