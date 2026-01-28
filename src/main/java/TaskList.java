import java.util.ArrayList;
import java.time.LocalDate;

public class TaskList {
    private ArrayList<Task> taskList;
    public int numTasks;

    TaskList() {
        this.taskList = new ArrayList<Task>(100);
        this.numTasks = 0;
    }

    Task get(int taskNum) {
        return this.taskList.get(taskNum - 1);
    }

    void add(Task task) {
        this.taskList.add(task);
        this.numTasks++;
    }

    // Add todos to the task list.
    Todo addTodo(String userInput) throws FroggeException{
        String taskName = Parser.getTodoDescription(userInput);
        Todo todo = new Todo(taskName);
        this.taskList.add(todo);
        this.numTasks++;
        return todo;
    }

    // Add deadlined tasks to the task list.
    Deadline addDeadline(String userInput) throws FroggeException {
        String desc = Parser.getDeadlineDescription(userInput);
        LocalDate deadline = Parser.getDeadline(userInput);
        Deadline task = new Deadline(desc, deadline);
        this.taskList.add(task);
        this.numTasks++;
        return task;
    }

    // Add events to the task list.
    Event addEvent(String userInput) throws FroggeException {
        String desc = Parser.getEventDescription(userInput);
        LocalDate start = Parser.getStart(userInput);
        LocalDate end = Parser.getEnd(userInput);
        Event task = new Event(desc, start, end);
        this.taskList.add(task);
        this.numTasks++;    
        return task;
    }

    void list() {
        for (int i = 0; i < this.numTasks; i++) {
            System.out.println(i + 1 + ". " + this.taskList.get(i));
        }
    }

    Task mark(int taskNum) throws FroggeException {
        try {
            this.taskList.get(taskNum - 1).mark();
            return this.taskList.get(taskNum - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* this task doesn't exist.");
        }
    }

    Task unmark(int taskNum) throws FroggeException {
        try {
            this.taskList.get(taskNum - 1).unmark();
            return this.taskList.get(taskNum - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* this task doesn't exist.");
        }
    }

    Task delete(int taskNum) throws FroggeException {
        try {
            this.numTasks--;
            return this.taskList.remove(taskNum - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* this task doesn't exist.");
        }
    }
}
