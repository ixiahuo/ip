import java.util.Arrays;

public class Deadline extends Task {
    private final String deadline;

    Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    static String getDescription(String userInput) throws FroggeException {
        return Arrays.stream(userInput.split(" "))
                .skip(1)
                .takeWhile(x -> !x.equals("/by"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need a description! " + 
                    "Follow the following format for deadline:\n" + 
                    "   deadline [description] /by [deadline]"));
    }

    static String getDeadline(String userInput) throws FroggeException {
        try {
            return userInput.split("/by ")[1];
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* I need a deadline! " +
                    "Follow the following format for deadline:\n" + 
                    "   deadline [description] /by [deadline]");
        }
    }

    String getSaveString() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.name + " | " + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
