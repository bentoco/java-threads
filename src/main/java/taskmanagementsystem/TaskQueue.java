package taskmanagementsystem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public void addTask(Task task) {
        queue.add(task);
//        notifyAll();
    }

    public Task getTask() {
        while (queue.isEmpty()) {
            try {
                wait();
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return queue.poll();
    }
}
