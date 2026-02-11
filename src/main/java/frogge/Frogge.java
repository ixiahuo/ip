package frogge;

public class Frogge {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    Frogge() {
        this.ui = new Ui();
        this.taskList = new TaskList();
        this.storage = new Storage(taskList);
        try {
            storage.init();
        } catch (FroggeException e) {
            ui.printError(e);
            this.taskList = new TaskList();
        }
    }

    public String executeTodo(String input) {
        try {
            Todo todo = this.taskList.addTodo(input);
            this.storage.append(todo);
            return this.ui.display("added:", todo);
        } catch (FroggeException e) {
            return ui.printError(e) + this.ui.display("format:", "todo [description]");
        }
    }

    public String executeDeadline(String input) {
        try {
            Deadline deadline = this.taskList.addDeadline(input);
            this.storage.append(deadline);
            return this.ui.display("added:", deadline);
        } catch (FroggeException e) {
            return ui.printError(e) + this.ui.display("format:", "deadline [description] /by [yyyy-mm-dd]");
        }
    }

    public String executeEvent(String input) {
        try {
            Event event = this.taskList.addEvent(input);
            this.storage.append(event);
            return this.ui.display("added:", event);
        } catch (FroggeException e) {
            return ui.printError(e) + this.ui.display("format:",
                    "event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
        }
    }

    public String executeList() {
        return this.ui.display(this.taskList.numTasks + " items in your list:\n") 
                + this.taskList.list();
    }

    public String executeMark(String input) {
        try {
            String oldSaveString = this.taskList
            .get(Parser.getTaskNum(input))
            .getSaveString();

            Task task = this.taskList.mark(Parser.getTaskNum(input));

            String newSaveString = this.taskList
                    .get(Parser.getTaskNum(input))
                    .getSaveString();
            this.storage.update(oldSaveString, newSaveString);

            return ui.display("marked", task);
        } catch (FroggeException e) {
            return ui.printError(e) + ui.display("format:", "mark [task number]");
        }
    }

    public String executeUnmark(String input) {
        try {
            String oldSaveString = this.taskList
            .get(Parser.getTaskNum(input))
            .getSaveString();

            Task task = this.taskList.unmark(Parser.getTaskNum(input));

            String newSaveString = this.taskList
                    .get(Parser.getTaskNum(input))
                    .getSaveString();
            this.storage.update(oldSaveString, newSaveString);

            return ui.display("unmarked", task);
        } catch (FroggeException e) {
            return ui.printError(e) + ui.display("format:", "unmark [task number]");
        }
    }

    public String executeDelete(String input) {
        try {
            Task deleted = this.taskList.delete(Parser.getTaskNum(input));
            this.storage.delete(deleted.getSaveString());
            return ui.display("deleted:", deleted);
        } catch (FroggeException e) {
            return ui.printError(e) + ui.display("format:", "delete [task number]");
        }
    }

    public String executeFind(String input) {
        try {
            TaskList foundTasks = this.taskList.find(input);
            return ui.display("found:", foundTasks);
        } catch (FroggeException e) {
            return ui.printError(e) + ui.display("format:", "find [keyword]");
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello!");
    }

    public String initGreeting() {
        return this.ui.printStart();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String command = Parser.getCommand(input);
        switch (command) {
        case "bye":
            return ui.printExit();
        case "todo":
            return executeTodo(input);
        case "deadline":
            return executeDeadline(input);
        case "event":
            return executeEvent(input);
        case "list":
            return executeList();
        case "mark":
            return executeMark(input);
        case "unmark":
            return executeUnmark(input);
        case "delete":
            return executeDelete(input);
        case "find" :
            return executeFind(input);
        default:
            return ui.printError(new FroggeException("*ribbit* i don't understand what you're saying! >~<"));
        }
    }
}
