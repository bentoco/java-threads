import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class StreamParallel {

    public static void main(String[] args) {
        Instant init = Instant.now();
        Map<Double, Double> map = new ConcurrentHashMap<>();
        IntStream.range(1, 10000000)
//                .parallel()
                .mapToDouble(n -> Math.pow(n, 5))
                .filter(n -> n % 2 == 0)
                .forEach(n -> map.put(n, Math.pow(n, 5)));
        Instant end = Instant.now();
        System.out.println(Duration.between(init, end));
    }
}
