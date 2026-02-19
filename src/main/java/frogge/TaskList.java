package frogge;

import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;

/**
 * Encapsulates the ArrayList containing Task objects.
 */
class TaskList {
    private ArrayList<Task> taskList;
    private int totalTasks;
    private int doneTasks;
    private final static int MAX_NUMBER_OF_TASKS = 100;

    /**
     * Constructor for TaskList.
     */
    TaskList() {
        this.taskList = new ArrayList<Task>(MAX_NUMBER_OF_TASKS);
        this.totalTasks = 0;
        this.doneTasks = 0;
    }

    public int getTotalTasks() {
        return this.totalTasks;
    }

    /**
     * Getter for number of tasks that have been marked as done.
     * @return number of tasks that have been marked as done.
     */
    public int getDoneTasks() {
        return this.doneTasks;
    }

    /**
     * Increments the number of done tasks by one.
     */
    public void increaseDoneTasks() {
        this.doneTasks += 1;
    }

    /**
     * Decrements the number of done tasks by one.
     */
    public void decreaseDoneTasks() {
        this.doneTasks -= 1;
    }

    /**
     * Returns the task at the specified position.
     * @param taskNum The position of the task to get, starting from 1.
     * @return Task at the specified position.
     */
    Task get(int taskNum) {
        return this.taskList.get(taskNum - 1);
    }

    /**
     * Appends the task.
     * @param task Task to be added.
     */
    void add(Task task) {
        this.taskList.add(task);
        this.totalTasks++;
    }

    /**
     * Adds a Todo object to the end of the list, corresponding to the given input.
     * @param userInput Input to be parsed.
     * @return Todo object corresponding to userInput that was added.
     * @throws FroggeException If no description is provided.
     */
    Todo addTodo(String userInput) throws FroggeException{
        String taskName = Parser.getTodoDescription(userInput);
        Todo todo = new Todo(taskName);
        this.taskList.add(todo);
        this.totalTasks++;
        return todo;
    }

    /**
     * Adds a Deadline object to the end of the list, corresponding to the given input.
     * @param userInput Input to be parsed.
     * @return Deadline object corresponding to userInput that was added.
     * @throws FroggeException If no description or deadline is provided.
     */
    Deadline addDeadline(String userInput) throws FroggeException {
        String desc = Parser.getDeadlineDescription(userInput);
        LocalDate deadline = Parser.getDeadline(userInput);
        Deadline task = new Deadline(desc, deadline);
        this.taskList.add(task);
        this.totalTasks++;
        return task;
    }

    /**
     * Adds an Event object to the end of the list, corresponding to the given input.
     * @param userInput Input to be parsed.
     * @return Event object corresponding to userInput that was added.
     * @throws FroggeException If no description, start or end is provided.
     */
    Event addEvent(String userInput) throws FroggeException {
        String desc = Parser.getEventDescription(userInput);
        LocalDate start = Parser.getStart(userInput);
        LocalDate end = Parser.getEnd(userInput);
        
        Event task = new Event(desc, start, end);
        this.taskList.add(task);
        this.totalTasks++;    
        return task;
    }

    /**
     * Displays the full list.
     * Called using the "list" command.
     */
    String list() {
        String s = "";
        for (int i = 0; i < this.totalTasks; i++) {
           s += i + 1 + ". " + this.taskList.get(i) + "\n";
        }
        return s;
    }

    /**
     * Marks the task at the specified position as done.
     * @param taskNum The position of the task to mark, starting from 1.
     * @return Updated task.
     * @throws FroggeException If no taskNum is specified or if the specified position is invalid.
     */
    Task mark(int taskNum) throws FroggeException {
        try {
            this.taskList.get(taskNum - 1).mark();
            return this.taskList.get(taskNum - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* this task doesn't exist.");
        }
    }

    /**
     * Unmarks the task at the specified position as not done.
     * @param taskNum The position of the task to unmark, starting from 1.
     * @return Updated task.
     * @throws FroggeException If no taskNum is specified or if the specified position is invalid.
     */
    Task unmark(int taskNum) throws FroggeException {
        try {
            this.taskList.get(taskNum - 1).unmark();
            return this.taskList.get(taskNum - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* this task doesn't exist.");
        }
    }

    /**
     * Deletes the task at the specified position.
     * @param taskNum The position of the task to delete, starting from 1.
     * @return Deleted task.
     * @throws FroggeException If no taskNum is specified or if the specified position is invalid.
     */
    Task delete(int taskNum) throws FroggeException {
        try {
            this.totalTasks--;
            return this.taskList.remove(taskNum - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* this task doesn't exist.");
        }
    }

    TaskList find(String userInput) throws FroggeException {
        String keyword = Parser.getKeyword(userInput);
        TaskList foundTasks = new TaskList();
        for (int i = 0; i < this.totalTasks; i++) {
            if (this.taskList.get(i).description.contains(keyword)) {
                foundTasks.add(this.taskList.get(i));
            }
        }
        return foundTasks;
    }

    void sort() {
        for (int i = 0; i < this.totalTasks - 1; i++) {
            for (int j = 0; j < this.totalTasks - i - 1; j++) {
                Task left = this.taskList.get(j);
                Task right = this.taskList.get(j+1);
                if (left.compareTo(right) > 0) {
                    this.taskList.set(j, right);
                    this.taskList.set(j+1, left);
                }
            }
        }
    }
    

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < this.totalTasks; i++) {
            string += (i + 1) + ". " + this.taskList.get(i) + "\n  ";
        }
        return string;
    }
}
