package frogge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate deadline;

    Deadline(String name, LocalDate deadline) {
        super(name);
        this.deadline = deadline;
    }

    String getSaveString() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.name + " | " + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + 
                this.deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
