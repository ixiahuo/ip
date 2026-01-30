package frogge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event with a start and end date.
 */
class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Constructor for Event.
     * @param description Description of event.
     * @param start LocalDate representation of start date of event.
     * @param end LocalDate representation of end date of event.
     */
    Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Alternative constructor for Event.
     * @param description Description of event.
     * @param isDone Boolean representing whether the event is over.
     * @param start LocalDate representation of start date of event.
     * @param end LocalDate representation of end date of event.
     */
    Event(String description, boolean isDone, LocalDate start, LocalDate end) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    @Override
    String getSaveString() {
        return "E | " + (this.isDone ? "1" : "0") 
                + " | " + this.description 
                + " | " + this.start 
                + " | " + this.end 
                + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Event task) {
            return this.description.equals(task.description) 
                    && this.isDone == task.isDone 
                    && this.start.equals(task.start) 
                    && this.end.equals(task.end);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() 
                + " (from: " 
                + this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) 
                + " to: " 
                + this.end.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) 
                + ")";
    }
}
