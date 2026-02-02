package frogge;

public class Frogge {

    private Ui ui;
    private TaskList tasklist;
    private Storage storage;

    Frogge() {
        this.ui = new Ui();
        this.tasklist = new TaskList();
        this.storage = new Storage(tasklist);
        try {
            storage.init();
        } catch (FroggeException e) {
            ui.printError(e);
            this.tasklist = new TaskList();
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
            try {
                Todo todo = this.tasklist.addTodo(input);
                this.storage.append(todo);
                return this.ui.display("added:", todo);
            } catch (FroggeException e) {
                return ui.printError(e) + this.ui.display("format:", "todo [description]");
            }
        case "deadline":
            try {
                Deadline deadline = this.tasklist.addDeadline(input);
                this.storage.append(deadline);
                return this.ui.display("added:", deadline);
            } catch (FroggeException e) {
                return ui.printError(e) + this.ui.display("format:", "deadline [description] /by [yyyy-mm-dd]");
            }
        case "event":
            try {
                Event event = this.tasklist.addEvent(input);
                this.storage.append(event);
                return this.ui.display("added:", event);
            } catch (FroggeException e) {
                return ui.printError(e) + this.ui.display("format:",
                        "event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
            }
        case "list":
            return this.ui.display(this.tasklist.numTasks + " items in your list:\n") + this.tasklist.list();
        case "mark":
            try {
                String oldSaveString = this.tasklist
                        .get(Parser.getTaskNum(input))
                        .getSaveString();

                Task task = this.tasklist.mark(Parser.getTaskNum(input));

                String newSaveString = this.tasklist
                        .get(Parser.getTaskNum(input))
                        .getSaveString();
                this.storage.update(oldSaveString, newSaveString);

                return ui.display("marked", task);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "mark [task number]");
            }
        case "unmark":
            try {
                String oldSaveString = this.tasklist
                        .get(Parser.getTaskNum(input))
                        .getSaveString();

                Task task = this.tasklist.unmark(Parser.getTaskNum(input));

                String newSaveString = this.tasklist
                        .get(Parser.getTaskNum(input))
                        .getSaveString();

                this.storage.update(oldSaveString, newSaveString);

                return ui.display("unmarked:", task);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "unmark [task number]");
            }
        case "delete":
            try {
                Task deleted = this.tasklist.delete(Parser.getTaskNum(input));
                this.storage.delete(deleted.getSaveString());
                return ui.display("deleted:", deleted);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "delete [task number]");
            }
        case "find" :
            try {
                TaskList foundTasks = this.tasklist.find(input);
                return ui.display("found:", foundTasks);
            } catch (FroggeException e) {
                return ui.printError(e) + ui.display("format:", "find [keyword]");
            }
        default:
            return ui.printError(new FroggeException("*ribbit* i don't understand what you're saying! >~<"));
        }
    }
}
