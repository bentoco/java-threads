import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerExample {

    /**
     * Data exchange between threads
     **/

    private static final Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            String r = exchange("money");
            String name = Thread.currentThread().getName();
            System.out.println(name + ": customer receive -> " + r);
        };

        Runnable r2 = () -> {
            String r = exchange("product");
            String name = Thread.currentThread().getName();
            System.out.println(name + ": salesman receive -> " + r);
        };

        executorService.submit(r1);
        executorService.submit(r2);

        executorService.shutdown();
    }

    private static String exchange(String value) {
        try {
            return exchanger.exchange(value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return "transaction not completed";
        }
    }
}
