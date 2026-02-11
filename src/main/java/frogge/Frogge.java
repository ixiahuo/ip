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

    public String executeTodo(String input) throws FroggeException {
        Todo todo = this.taskList.addTodo(input);
        this.storage.append(todo);
        return this.ui.display("added:", todo);
    }

    public String executeDeadline(String input) throws FroggeException {
        Deadline deadline = this.taskList.addDeadline(input);
        this.storage.append(deadline);
        return this.ui.display("added:", deadline);
    }

    public String executeEvent(String input) throws FroggeException {
        Event event = this.taskList.addEvent(input);
        this.storage.append(event);
        return this.ui.display("added:", event);
    }

    public String executeMark(String input) throws FroggeException {
        String oldSaveString = this.taskList
        .get(Parser.getTaskNum(input))
        .getSaveString();

        Task task = this.taskList.mark(Parser.getTaskNum(input));

        String newSaveString = this.taskList
                .get(Parser.getTaskNum(input))
                .getSaveString();
        this.storage.update(oldSaveString, newSaveString);

        return ui.display("marked", task);
    }

    public String executeUnmark(String input) throws FroggeException {
        String oldSaveString = this.taskList
        .get(Parser.getTaskNum(input))
        .getSaveString();

        Task task = this.taskList.unmark(Parser.getTaskNum(input));

        String newSaveString = this.taskList
                .get(Parser.getTaskNum(input))
                .getSaveString();
        this.storage.update(oldSaveString, newSaveString);

        return ui.display("unmarked", task);
    }

    public String executeDelete(String input) throws FroggeException {
        Task deleted = this.taskList.delete(Parser.getTaskNum(input));
        this.storage.delete(deleted.getSaveString());
        return ui.display("deleted:", deleted);
    }

    public String executeFind(String input) throws FroggeException {
        TaskList foundTasks = this.taskList.find(input);
        return ui.display("found:", foundTasks);
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
            try {
                return executeTodo(input);
            } catch (FroggeException e) {
                assert(input.split(" ").length < 1);
                return ui.printError(e) + this.ui.display("format:", "todo [description]");
            }
        case "deadline":
            try {
                return executeDeadline(input);
            } catch (FroggeException e) {
                return ui.printError(e) + this.ui.display("format:", "deadline [description] /by [yyyy-mm-dd]");
            }
        case "event":
            try {
                return executeEvent(input);
            } catch (FroggeException e) {
                return ui.printError(e) + this.ui.display("format:",
                        "event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
            }
        case "list":
            // return each item in the list
            return this.ui.display(this.taskList.numTasks + " items in your list:\n") 
                    + this.taskList.list();
        case "mark":
            try {
                return executeMark(input);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "mark [task number]");
            }
        case "unmark":
            try {
                return executeUnmark(input);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "unmark [task number]");
            }
        case "delete":
            try {
                return executeDelete(input);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "delete [task number]");
            }
        case "find" :
            try {
                return executeFind(input);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "find [keyword]");
            }
        default:
            return ui.printError(new FroggeException("*ribbit* i don't understand what you're saying! >~<"));
        }
    }
}
