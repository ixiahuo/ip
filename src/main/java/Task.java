public class Task {
    protected final String name;
    protected final boolean isDone;

    Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    protected Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }
    
    Task mark() {
        return new Task(this.name, true);
    }

    Task unmark() {
        return new Task(this.name, false);
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X]" : "[ ]") + " " + this.name;
    }
}
