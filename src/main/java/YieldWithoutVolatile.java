public class YieldWithoutVolatile {
    private static int n = 0;
    private static boolean isReady = false;

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread t0 = new Thread(myRunnable);
        t0.start();

        n = 42;
//        isReady = true;
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (!isReady) {
                /**
                 * yield, when invoked we tell the task controller to move
                 * the task back to the end of the queue for re-execution and
                 * allow other tasks to be processed. **/
                Thread.yield();
            }
            System.out.println(n);
        }
    }
}
