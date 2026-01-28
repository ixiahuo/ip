import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

public class Deadline extends Task {
    private final LocalDate deadline;

    Deadline(String name, LocalDate deadline) {
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

    static LocalDate getDeadline(String userInput) throws FroggeException {
        try {
            return LocalDate.parse(userInput.split("/by ")[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* I need a deadline! " +
                    "Follow the following format for deadline:\n" + 
                    "   deadline [description] /by [yyyy-mm-dd]");
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* Choose a valid date! >:(");
        }
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
