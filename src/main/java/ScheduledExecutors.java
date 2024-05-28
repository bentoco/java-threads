import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutors {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        //executor.schedule(new Task(), 1, TimeUnit.SECONDS);
        //executor.scheduleAtFixedRate(new Task(), 0, 2, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(new Task(), 0, 2, TimeUnit.SECONDS);
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(LocalDateTime.now());
            String name = Thread.currentThread().getName();
            int n = new Random().nextInt(1000);
            System.out.println(name + " " + n);
        }
    }
}
