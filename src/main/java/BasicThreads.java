public class BasicThreads {

    public static void main(String[] args) {
        // base thread
        Thread t = Thread.currentThread();
        printThreadInfo(t);

        Worker worker = new Worker();

        // new thread
        Thread t1 = new Thread(worker);
        Thread t2 = new Thread(worker);

        // runnable as lambda
        Thread t3 = new Thread(() -> printThreadInfo(Thread.currentThread()));

        printThreadInfo(t1);
        printThreadInfo(t2);

        // start thread
        t1.start();
        t2.start();
        t3.start();
    }

    private static void printThreadInfo(Thread t) {
        System.out.println("Thread id: " + t.threadId());
        System.out.println("Thread name: " + t.getName());
        System.out.println("Thread group: " + t.getThreadGroup());
        System.out.println("Thread state: " + t.getState());
        System.out.println("Thread priority: " + t.getPriority());
        System.out.println("--------------------------");
    }
}
