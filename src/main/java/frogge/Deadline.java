package frogge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
class Deadline extends Task {
    private LocalDate deadline;

    /**
     * Constructor for Deadline.
     * @param description Description of task.
     * @param deadline Date that the task must be done by.
     */
    Deadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Alternative constructor for Deadline.
     * @param description Description of task.
     * @param isDone Boolean representing whether the task is done.
     * @param deadline Date that the task must be done by.
     */
    Deadline(String description, boolean isDone, LocalDate deadline) {
        super(description, isDone);
        this.deadline = deadline;
    }

    @Override
    String getSaveString() {
        return "D | " + (this.isDone ? "1" : "0") 
                + " | " + this.description
                + " | " + this.deadline 
                + "\n";
    }

    @Override
    public int compareTo(Task other) {
        int doneCompare = super.compareTo(other);
        if (super.compareTo(other) == 0) {
            if (other instanceof Event || other instanceof Todo) {
                return -1;
            } else if (other instanceof Deadline d) {
                return this.deadline.compareTo(d.deadline);
            }
        }
        return doneCompare;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Deadline task) {
            return this.description.equals(task.description) 
                    && this.isDone == task.isDone 
                    && this.deadline.equals(task.deadline);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() 
                + " (by: " 
                + this.deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) 
                + ")";
    }
}
