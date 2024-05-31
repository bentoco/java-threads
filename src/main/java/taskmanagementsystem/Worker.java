package taskmanagementsystem;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Worker implements Runnable {

    private final TaskQueue queue;
    private final Semaphore semaphore;
    private final CountDownLatch latch;

    public Worker(TaskQueue queue, Semaphore semaphore, CountDownLatch latch) {
        this.queue = queue;
        this.semaphore = semaphore;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphore.acquire();
                Task task = queue.getTask();
                if(task == null) break;
                task.run();
                semaphore.release();
                latch.countDown();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
