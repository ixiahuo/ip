package frogge;

import java.util.Scanner;

/**
 * Main class that the Frogge chat bot runs from.
 */
class Frogge {
    private Ui ui;
    private Storage storage;
    private TaskList tasklist;

    /**
     * Constructor for Frogge.
     * Initialises objects of class Ui, TaskList and Storage.
     */
    Frogge() {
        this.ui = new Ui();
        this.tasklist = new TaskList();
        this.storage = new Storage(tasklist);

        // Initialises the task list.
        try {
            storage.init();
        } catch (FroggeException e) {
            ui.printError(e);
            this.tasklist = new TaskList();
        }
    }

    /**
     * Main functionality of the Frogge chat bot.
     * Performs different tasks based on the user input given.
     */
    void run() {
        ui.printStart();
        ui.showLine();

        Scanner inputScanner = new Scanner(System.in);
        String userInput = inputScanner.nextLine();
        String command = Parser.getCommand(userInput);

        while (!command.equals("bye")) {
            this.ui.showLine();
            switch (command) {
            case "todo":
                try {
                    Todo todo = this.tasklist.addTodo(userInput);
                    this.storage.append(todo);
                    this.ui.display("added:", todo);
                } catch (FroggeException e) {
                    ui.printError(e);
                    this.ui.display("format:", "todo [description]");
                }
                break;
            case "deadline":
                try {
                    Deadline deadline = this.tasklist.addDeadline(userInput);
                    this.storage.append(deadline);
                    this.ui.display("added:", deadline);
                } catch (FroggeException e) {
                    ui.printError(e);
                    this.ui.display("format:", "deadline [description] /by [yyyy-mm-dd]");
                }
                break;
            case "event":
                try {
                    Event event = this.tasklist.addEvent(userInput);
                    this.storage.append(event);
                    this.ui.display("added:", event);
                } catch (FroggeException e) {
                    ui.printError(e);
                    this.ui.display("format:",
                            "event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
                }
                break;
            case "list":
                this.ui.display(this.tasklist.numTasks + " items in your list:");
                this.tasklist.list();
                break;
            case "mark":
                try {
                    String oldSaveString = this.tasklist
                            .get(Parser.getTaskNum(userInput))
                            .getSaveString();

                    Task task = this.tasklist.mark(Parser.getTaskNum(userInput));

                    String newSaveString = this.tasklist
                            .get(Parser.getTaskNum(userInput))
                            .getSaveString();
                    this.storage.update(oldSaveString, newSaveString);

                    ui.display("marked", task);
                } catch (FroggeException e) {
                    ui.printError(e);
                    ui.display("format:", "mark [task number]");
                }
                break;
            case "unmark":
                try {
                    String oldSaveString = this.tasklist
                            .get(Parser.getTaskNum(userInput))
                            .getSaveString();

                    Task task = this.tasklist.unmark(Parser.getTaskNum(userInput));

                    String newSaveString = this.tasklist
                            .get(Parser.getTaskNum(userInput))
                            .getSaveString();

                    this.storage.update(oldSaveString, newSaveString);

                    ui.display("unmarked:", task);
                } catch (FroggeException e) {
                    ui.printError(e);
                    ui.display("format:", "unmark [task number]");
                }
                break;
            case "delete":
                try {
                    Task deleted = this.tasklist.delete(Parser.getTaskNum(userInput));
                    this.storage.delete(deleted.getSaveString());
                    ui.display("deleted:", deleted);
                } catch (FroggeException e) {
                    ui.printError(e);
                    ui.display("format:", "delete [task number]");
                }
                break;
            default:
                ui.printError(new FroggeException("*ribbit* i don't understand what you're saying! >~<"));
            }
            this.ui.showLine();
            userInput = inputScanner.nextLine();
            command = Parser.getCommand(userInput);
        }
        inputScanner.close();
    }

    public static void main(String[] args) {
        new Frogge().run();
    }

}
