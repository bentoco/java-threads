import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Using conventional collections with multithreaded processes can
 * also lead to inconsistencies in operations. To do this we must use
 * specific objects for synchronous processes.
 **/
public class SynchronizedCollections {

    /**
     * For test this behavior, shift the lists.
     **/
    private static final List<String> simpleList = new ArrayList<>();
    private static final List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());

    /**
     * Collections.synchronizedCollection(new ArrayList<>());
     * Collections.synchronizedSet(new ArrayList<>());
     * Collections.synchronizedMap(new ArrayList<>());
     **/


    public static void main(String[] args) throws InterruptedException {
        MyRunnable myRunnable = new MyRunnable();

        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);

        t0.start();
        t1.start();
        t2.start();

        Thread.sleep(500);
        System.out.println(synchronizedList);
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            synchronizedList.add("Foo Bar");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inseriu na lista!");
        }
    }
}
