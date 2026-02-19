package frogge;

/**
 * Generic super class for Todo, Deadline and Event.
 * Represents a generic task object.
 */
abstract class Task implements Comparable<Task> {
    protected final String description;
    protected boolean isDone;

    /**
     * Constructor for Task
     * Default value of isDone is false.
     * @param description Task description.
     */
    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Alternative constructor for Task
     * @param description Task description
     * @param isDone Boolean representing whether the task is done.
     */
    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    boolean getIsDone() {
        return this.isDone;
    }
    
    /**
     * Mark a task as done.
     */
    void mark() {
        this.isDone = true;
    }

    /**
     * Mark a task as not done.
     */
    void unmark() {
        this.isDone = false;
    }

    /**
     * Obtain a String representation of the Task used to write to the save file.
     * @return String used to write to the save file.
     */
    abstract String getSaveString();

    @Override
    public int compareTo(Task other) {
        if (this.isDone == false && other.isDone == true) {
            return -1;
        } else if (this.isDone == true && other.isDone == false) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Task task) {
            return this.description.equals(task.description) 
                && this.isDone == task.isDone;
        }
        return false;
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X]" : "[ ]") + " " + this.description;
    }
}
