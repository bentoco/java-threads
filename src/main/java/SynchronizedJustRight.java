public class SynchronizedJustRight {

    /*
    * One disadvantage of using the synchronized keyword in Java is that,
    * depending on how it is implemented, we may end up compromising parallelism.
    * It is crucial to apply synchronization only to the section of the code
    * where concurrency actually occurs. This minimizes the time during which resources are locked,
    * allowing for better performance and utilization of parallelism.
    * */

    private static int i = -1;

    public static void main(String[] args) {
        Concurrency.MyRunnable runnable = new Concurrency.MyRunnable();

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
            int j;

            // Here we are guaranteeing synchronisation only on the section where there is concurrency.
            synchronized (this) {
                i++;
                j = i * 2;
            }
            double jRaisedTo100 = Math.pow(j, 100);
            double sqrt = Math.sqrt(jRaisedTo100);
            System.out.println(sqrt);
        }
    }
}
