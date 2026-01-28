public class Task {
    protected final String name;
    protected boolean isDone;

    Task(String name) {
        this.name = name;
        this.isDone = false;
    }
    
    void mark() {
        this.isDone = true;
    }

    void unmark() {
        this.isDone = false;
    }

    String getSaveString() {
        return "";
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X]" : "[ ]") + " " + this.name;
    }
}
