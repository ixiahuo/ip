import java.util.Scanner;

public class Frogge {
    private Ui ui;
    private Storage storage;
    private TaskList tasklist;

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

    void run() {
        ui.printStart();
        ui.showLine();
        Scanner inputScanner = new Scanner(System.in);
        String userInput = inputScanner.nextLine();
        String command = Parser.getCommand(userInput);
        while (!command.equals("bye")) {
            this.ui.showLine();
            switch (command) {
            case "todo" :
                try {
                    Todo todo = this.tasklist.addTodo(userInput);
                    this.storage.append(todo);
                    this.ui.display("added:", todo);
                } catch (FroggeException e) {
                    ui.printError(e);
                }
                break;
            
            case "deadline" :
                try {
                    Deadline deadline = this.tasklist.addDeadline(userInput);
                    this.storage.append(deadline);
                    this.ui.display("added:", deadline);
                } catch (FroggeException e) {
                    ui.printError(e);
                }
                break;

            case "event" :
                try {
                    Event event = this.tasklist.addEvent(userInput);
                    this.storage.append(event);
                    this.ui.display("added:", event);
                } catch (FroggeException e) {
                    ui.printError(e);
                }
                break;

            case "list" :
                this.ui.display(this.tasklist.numTasks + " items in your list:");
                this.tasklist.list();
                break;

            case "mark" :
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
                }
                break;

            case "unmark" :
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
                }
                break;

            case "delete" :
                try {
                    Task deleted = this.tasklist.delete(Parser.getTaskNum(userInput));
                    this.storage.delete(deleted.getSaveString());
                    ui.display("deleted:", deleted);
                } catch (FroggeException e) {
                    ui.printError(e);
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
