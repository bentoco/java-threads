import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Critical Region and Mutex (Mutual exclusion) **/
public class ProducerConsumerProblem2 {
    private static final BlockingQueue<Integer> LIST = new LinkedBlockingQueue<>(5);
    private static volatile boolean producing = true;
    private static volatile boolean consuming = true;
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    simulatingProcess();
                    if (producing) {
                        LOCK.lock();
                        System.out.println("Producing");
                        int n = new Random().nextInt(10000);
                        LIST.add(n);
                        if (LIST.size() == 5) {
                            System.out.println("Pausing producer.");
                            producing = false;
                        }
                        if (LIST.size() == 1) {
                            System.out.println("Init consumer.");
                            consuming = true;
                        }
                        LOCK.unlock();
                    } else {
                        System.out.println("!!! Producer sleeping!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    simulatingProcess();
                    if (consuming) {
                        LOCK.lock();
                        System.out.println("Consuming");
                        Optional<Integer> n = LIST.stream().findFirst();
                        n.ifPresent(LIST::remove);
                        if (LIST.isEmpty()) {
                            System.out.println("Pausing consumer.");
                            consuming = false;
                        }
                        if (LIST.size() == 4) {
                            System.out.println("Init producer.");
                            producing = true;
                        }
                        LOCK.unlock();
                    } else {
                        System.out.println("??? Consumer sleeping!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Window.monitor(() -> String.valueOf(LIST.size()));

        producer.start();
        consumer.start();
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
}
