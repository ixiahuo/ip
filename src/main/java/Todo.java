import java.util.Arrays;

public class Todo extends Task {
    Todo(String name) {
        super(name);
    }

    static String getDescription(String userInput) throws FroggeException {
        return Arrays.stream(userInput.split(" "))
                .skip(1)
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need a description! Follow the following format for todo:\n" + 
                "   todo [description]"));
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
