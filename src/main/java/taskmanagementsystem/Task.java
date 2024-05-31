package taskmanagementsystem;

public class Task {

    private final int id;
    private final int duration;

    public Task(int id, int duration) {
        this.id = id;
        this.duration = duration;
    }

    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("Task " + id + " is running with " + name + ".");
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task " + id + " completed.");
    }
}
