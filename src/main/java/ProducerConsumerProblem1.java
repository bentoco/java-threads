import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/** Race-condition and Deadlock **/
public class ProducerConsumerProblem1 {
    private static final List<Integer> LIST = new ArrayList<>(5);
    private static boolean producing = true;
    private static boolean consuming = true;

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    simulatingProcess();
                    if (producing) {
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
