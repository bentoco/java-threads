import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Atomic objects perform their actions in a single operation,
 * preventing the process from being interrupted. This ensures
 * that one thread interrupts the operation of another.
 **/
public class Atomics {

    private static int i = -1;

    private static final AtomicInteger atomicInteger = new AtomicInteger(-1);
    private static final AtomicBoolean atomicBool = new AtomicBoolean(false);
    private static final AtomicReference<Object> atomicReference = new AtomicReference<>(new Object());

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();

        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        Thread t4 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
//            i++;
            String name = Thread.currentThread().getName();
//            System.out.println(name + ": " + i);
//            System.out.println(name + ": " + atomicInteger.incrementAndGet());
//            System.out.println(name + ": " + atomicBool.compareAndExchange(false, true));
//            System.out.println(name + ": " + atomicReference.getAndSet());
        }
    }
}
