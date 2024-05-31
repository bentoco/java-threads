package taskmanagementsystem;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int NUM_WORKERS = 5;
    private static final int NUM_TASKS = 10;
    private static final int SEMAPHORE_LIMIT = 3;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_WORKERS);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM_WORKERS);
        Semaphore semaphore = new Semaphore(SEMAPHORE_LIMIT);
        CountDownLatch latch = new CountDownLatch(NUM_TASKS);
        TaskQueue taskQueue = new TaskQueue();

        for (int i = 0; i < NUM_TASKS; i++) {
            taskQueue.addTask(new Task(i, 1000));
        }

        for (int i = 0; i < NUM_WORKERS; i++) {
            executorService.submit(new Worker(taskQueue, semaphore, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
            System.out.println("All tasks completed.");
        }
    }
}
