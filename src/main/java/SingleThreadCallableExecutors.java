import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SingleThreadCallableExecutors {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newSingleThreadExecutor();

            Future<String> submitted = executorService.submit(new MyRunnable());

            System.out.println(submitted.isDone());
            System.out.println(submitted.get());
            System.out.println(submitted.get(5, TimeUnit.SECONDS));
            System.out.println(submitted.isDone());
        } catch (Exception e) {
            throw e;
        } finally {
            if (executorService != null)
                executorService.shutdownNow();
        }
    }

    static class MyRunnable implements Callable<String> {
        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            Thread.State state = Thread.currentThread().getState();
            Integer number = new Random().nextInt(1000);
            System.out.printf("Hello from my runnable! T_NAME: %s; T_STATE: %s; T_NUMBER: %s;%n", name, state, number);
            return name + " " + number;
        }
    }
}
