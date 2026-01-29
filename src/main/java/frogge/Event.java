package frogge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    Event(String name, LocalDate start, LocalDate end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    Event(String name, boolean isDone, LocalDate start, LocalDate end) {
        super(name, isDone);
        this.start = start;
        this.end = end;
    }

    String getSaveString() {
        return "E | " + (this.isDone ? "1" : "0") +
                " | " + this.name +
                " | " + this.start +
                " | " + this.end + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Event task) {
            return this.name.equals(task.name) && this.isDone == task.isDone &&
                    this.start.equals(task.start) && this.end.equals(task.end);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + 
                " to: " + this.end.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
