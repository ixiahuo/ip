package frogge;

public class Todo extends Task {
    Todo(String name) {
        super(name);
    }

    String getSaveString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.name;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
