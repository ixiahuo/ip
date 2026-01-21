public class Event extends Task {
    private final String start;
    private final String end;

    Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
         " (from: " + this.start + 
         " to: " + this.end + ")";
    }
}
