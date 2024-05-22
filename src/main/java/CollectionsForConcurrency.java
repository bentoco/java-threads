import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Some collections for specific scenarios have Thread-safe features by
 * default to be used.
 **/
public class CollectionsForConcurrency {

    /**
     * This class copies the entire list to perform the operation. be careful,
     * depending on the size of your list this can become a bottleneck,
     * use it for small lists.
     **/
    private static final List<String> copyOnWriteList = new CopyOnWriteArrayList<>();

    /**
     * Adds synchronisation to necessary parts of your functions
     **/
    private static final ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    /**
     * Queues are an alternative for dealing with multi-threads
     * as their mechanism helps with concurrency.
     **/
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();


    public static void main(String[] args) throws InterruptedException {
        MyRunnable myRunnable = new MyRunnable();

        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);

        t0.start();
        t1.start();
        t2.start();

        Thread.sleep(500);
//        System.out.println(copyOnWriteList);
//        System.out.println(map);
//        System.out.println(queue);
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
//            copyOnWriteList.add("Foo Bar");
//            map.put(new Random().nextInt(), "Foo bar");
//            queue.add("Foo bar");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inseriu na lista!");
        }
    }
}
