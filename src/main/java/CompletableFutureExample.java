import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    public static void main(String[] args) {
        CompletableFuture<String> process = doProcess();

        CompletableFuture<String> thenApply =
                process.thenApply(s -> s + " SECOND PHRASE");

        CompletableFuture<Void> thenAccept =
                thenApply.thenAccept(System.out::println);

        System.out.println("...");

        sleep();
        sleep();
        sleep();
    }

    private static CompletableFuture<String> doProcess() {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return "FIRST PHRASE";
        });
    }

    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
