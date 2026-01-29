package frogge;

class Todo extends Task {
    Todo(String name) {
        super(name);
    }

    Todo(String name, boolean isDone) {
        super(name, isDone);
    }

    String getSaveString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Todo task) {
            return this.name.equals(task.name) && this.isDone == task.isDone;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
