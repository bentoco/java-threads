public class YieldWithVolatile {
    /** volatile say to program ways take value from the disk, not from mem cache,
    * to avoid unsynchronised data **/
    private static volatile int n = 0;
    private static volatile boolean isReady = false;

    public static void main(String[] args) {
        while (true) {
            Thread t0 = new Thread(new MyRunnable());
            Thread t1 = new Thread(new MyRunnable());
            Thread t2 = new Thread(new MyRunnable());
            t0.start();
            t1.start();
            t2.start();

            n = 42;
            isReady = true;

            while (t0.getState() != Thread.State.TERMINATED
                    || t1.getState() != Thread.State.TERMINATED
                    || t2.getState() != Thread.State.TERMINATED
            ) {  /* waiting */ }

            n = 0;
            isReady = false;
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (!isReady)
                Thread.yield();
            if (n != 42)
                throw new IllegalStateException(String.format("Number is different than 42! %s", n));
        }
    }
}
