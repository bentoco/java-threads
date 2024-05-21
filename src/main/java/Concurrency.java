public class Concurrency {

    private static int i = -1;

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
        /* object doors
        static final Object lock1 = new Object();
        static final Object lock2 = new Object(); */

        /* Remove reserved word "synchronised" for tests.
        * By adding the reserved word "synchronised" only one
        * thread can execute the run method at a time,
        * then the values will be printed in sequence. */
        @Override
        public synchronized void run() {
            print();
            /* synchronized (lock1) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            }

            synchronized (lock2) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            } */
        }

        private static void print() {
            /* Inside static snippets we don't have objects to lock like ‘this’.
            * So to enable synchronisation at class level we can inform the object to be locked to the whole JVM.
            * Note: this rule is not valid for a distributed architecture running in several containers.*/
            synchronized (Concurrency.class) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            }
        }
    }
}
