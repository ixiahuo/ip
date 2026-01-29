package frogge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    private LocalDate deadline;

    Deadline(String name, LocalDate deadline) {
        super(name);
        this.deadline = deadline;
    }

    Deadline(String name, boolean isDone, LocalDate deadline) {
        super(name, isDone);
        this.deadline = deadline;
    }

    String getSaveString() {
        return "D | " + (this.isDone ? "1" : "0") +
                " | " + this.name +
                " | " + this.deadline + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Deadline task) {
            return this.name.equals(task.name) && this.isDone == task.isDone &&
                    this.deadline.equals(task.deadline);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + 
                this.deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
