package frogge;

/**
 * Subclass of Task.
 */
class Todo extends Task {
    /**
     * Constructor for Todo.
     * @param description Description of task.
     */
    Todo(String description) {
        super(description);
    }

    /**
     * Alternative constructor for Todo.
     * @param description Description of task.
     * @param isDone Boolean representing whether the task is done.
     */
    Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    String getSaveString() {
        return "T | " 
                + (this.isDone ? "1" : "0") 
                + " | " + this.description 
                + "\n";
    }

    @Override
    public int compareTo(Task other) {
        int doneCompare = super.compareTo(other);
        if (super.compareTo(other) == 0) {
            if (other instanceof Deadline || other instanceof Event) {
                return 1;
            }
        }
        return doneCompare;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Todo task) {
            return this.description.equals(task.description) 
                    && this.isDone == task.isDone;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
