import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Using the right tools
 **/
public class ProducerConsumerProblem3 {
    private static final BlockingQueue<Integer> LIST = new LinkedBlockingQueue<>(5);

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            while (true) {
                try {
//                    simulatingProcess();
                    simulatingProcessSlowly();
                    System.out.println("Producing");
                    int n = new Random().nextInt(10000);
                    LIST.put(n);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    simulatingProcess();
//                    simulatingProcessSlowly();
                    System.out.println("Consuming");
                    LIST.take();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Window.monitor(() -> String.valueOf(LIST.size()));

        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(2);
        executor.scheduleWithFixedDelay(producer, 0, 10, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(consumer, 0, 10, TimeUnit.MILLISECONDS);
    }

    private static void simulatingProcess() {
        int tempo = new Random().nextInt(40);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void simulatingProcessSlowly() {
        int tempo = new Random().nextInt(400);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
