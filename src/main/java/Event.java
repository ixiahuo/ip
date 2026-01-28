import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    Event(String name, LocalDate start, LocalDate end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    static String getDescription(String userInput) throws FroggeException {
        return Arrays.stream(userInput.split(" "))
            .skip(1)
            .takeWhile(x -> !x.equals("/from") && !x.equals("/to"))
            .reduce((x,y) -> x + " " + y)
            .orElseThrow(() -> new FroggeException("*ribbit* I need a description! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]"));
    }

    static LocalDate getStart(String userInput) throws FroggeException {
        String[] args = userInput.split(" ");
        int fromIndex;
        for (fromIndex = 0; fromIndex < args.length; fromIndex++) {
            if (args[fromIndex].equals("/from")) {
                break;
            }
        }
        if (fromIndex == args.length) {
            throw new FroggeException("*ribbit* I need a start time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
        }
        try {
            return LocalDate.parse(Arrays.stream(args, fromIndex, args.length)
                    .skip(1)
                    .takeWhile(x -> !x.equals("/to"))
                    .reduce((x,y) -> x + " " + y)
                    .orElseThrow(() -> new FroggeException("*ribbit* I need a start time! " + 
                        "Follow the following format for event:\n" + 
                        "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]")));
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* Choose a valid date >:(");
        }
    }

    static LocalDate getEnd(String userInput) throws FroggeException {
        String[] args = userInput.split(" ");
        int toIndex;
        for (toIndex = 0; toIndex < args.length; toIndex++) {
            if (args[toIndex].equals("/to")) {
                break;
            }
        }
        if (toIndex == args.length) {
            throw new FroggeException("*ribbit* I need an end time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
        }
        try {
            return LocalDate.parse(Arrays.stream(args, toIndex, args.length)
                .skip(1)
                .takeWhile(x -> !x.equals("/from"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need an end time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]")));
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* Choose a valid date >:(");
        }
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
