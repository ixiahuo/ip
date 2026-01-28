import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    Event(String name, LocalDate start, LocalDate end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    String getSaveString() {
        return "E | " + (this.isDone ? "1" : "0") +
                 " | " + this.name +
                 " | " + this.start +
                  " | " + this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
         " (from: " + this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + 
         " to: " + this.end.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
