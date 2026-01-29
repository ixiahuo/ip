package frogge;

class Task {
    protected final String name;
    protected boolean isDone;

    Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }
    
    void mark() {
        this.isDone = true;
    }

    void unmark() {
        this.isDone = false;
    }

    String getSaveString() {
        return "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Task task) {
            return this.name.equals(task.name) && this.isDone == task.isDone;
        }
        return false;
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X]" : "[ ]") + " " + this.name;
    }
}
