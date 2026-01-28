import java.util.Arrays;

public class Event extends Task {
    private final String start;
    private final String end;

    Event(String name, String start, String end) {
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
                    "   event [description] /from [start] /to [end]"));
    }

    static String getStart(String userInput) throws FroggeException {
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
                    "   event [description] /from [start] /to [end]");
        }
        return Arrays.stream(args, fromIndex, args.length)
                .skip(1)
                .takeWhile(x -> !x.equals("/to"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need a start time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [start] /to [end]"));
    }

    static String getEnd(String userInput) throws FroggeException {
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
                    "   event [description] /from [start] /to [end]");
        }
        return Arrays.stream(args, toIndex, args.length)
                .skip(1)
                .takeWhile(x -> !x.equals("/from"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need an end time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [start] /to [end]"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
         " (from: " + this.start + 
         " to: " + this.end + ")";
    }
}
