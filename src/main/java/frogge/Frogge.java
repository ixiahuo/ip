package frogge;

import java.io.IOException;

/** 
 * Represents the chat bot entity. 
*/
public class Frogge {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    public boolean isCorrupted;

    Frogge() {
        this.ui = new Ui();
        this.taskList = new TaskList();
        this.storage = new Storage(taskList);
        this.isCorrupted = false;
        try {
            storage.init();
        } catch (FroggeException e) {
            this.isCorrupted = true;
            this.taskList = new TaskList();
        }
    }

    /**
     * Gets Frogge's health, which represents the fraction of tasks that are done.
     * @return the number of done tasks over the number of total tasks.
     */
    public double getHealth() {
        if (this.taskList.getTotalTasks() == 0) {
            return 1.0;
        }
        return (double) this.taskList.getDoneTasks() / this.taskList.getTotalTasks();
    }

    /**
     * Executes the todo command.
     * Creates a Todo object and adds it to the TaskList before displaying a validation message.
     * @param input User input to be parsed by Parser to extract the Todo object information.
     * @return Validation message as a String.
     */
    public String executeTodo(String input) {
        try {
            Todo todo = this.taskList.addTodo(input);
            this.storage.append(todo);
            return this.ui.display("added:", todo);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n" 
                    + this.ui.display("format:", "todo [description]");
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    /**
     * Executes the deadline command.
     * Creates a Deadline object and adds it to the TaskList before displaying a validation message.
     * @param input User input to be parsed by Parser to extract the Deadline object information.
     * @return Validation message as a String.
     */
    public String executeDeadline(String input) {
        try {
            Deadline deadline = this.taskList.addDeadline(input);
            this.storage.append(deadline);
            return this.ui.display("added:", deadline);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n"
                    + this.ui.display("format:", "deadline [description] /by [yyyy-mm-dd]");
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    /**
     * Executes the event command.
     * Creates an Event object and adds it to the TaskList before displaying a validation message.
     * @param input User input to be parsed by Parser to extract the Event object information.
     * @return Validation message as a String.
     */
    public String executeEvent(String input) {
        try {
            Event event = this.taskList.addEvent(input);
            this.storage.append(event);
            return this.ui.display("added:", event);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n"
                    + this.ui.display("format:",
                    "event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    /**
     * Executes the list command.
     * Concatenates all tasks in the TaskList as Strings into one String.
     * @return Validation message and the TaskList as a String.
     */
    public String executeList() {
        return this.ui.display(this.taskList.getTotalTasks() + " items in your list:\n") 
                + this.taskList.list();
    }

    /**
     * Executes the mark command.
     * Marks the Task object specified by the number in the input and updates the 
     * save file accordingly.
     * @param input User input to be parsed by Parser to extract the number of the 
     * task to be marked.
     * @return Validation message as a String.
     */
    public String executeMark(String input) {
        try {
            String oldSaveString = this.taskList
            .get(Parser.getTaskNum(input))
            .getSaveString();

            Task task = this.taskList.mark(Parser.getTaskNum(input));
            this.taskList.increaseDoneTasks();

            String newSaveString = this.taskList
                    .get(Parser.getTaskNum(input))
                    .getSaveString();
            this.storage.update(oldSaveString, newSaveString);
            
            return ui.display("marked", task);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n" 
                    + ui.display("format:", "mark [task number]");
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    /**
     * Executes the unmark command.
     * UNmarks the Task object specified by the number in the input and updates the 
     * save file accordingly.
     * @param input User input to be parsed by Parser to extract the number of the 
     * task to be unmarked.
     * @return Validation message as a String.
     */
    public String executeUnmark(String input) {
        try {
            String oldSaveString = this.taskList
            .get(Parser.getTaskNum(input))
            .getSaveString();

            Task task = this.taskList.unmark(Parser.getTaskNum(input));
            this.taskList.decreaseDoneTasks();
            
            String newSaveString = this.taskList
                    .get(Parser.getTaskNum(input))
                    .getSaveString();
            this.storage.update(oldSaveString, newSaveString);

            return ui.display("unmarked", task);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n"
                    + ui.display("format:", "unmark [task number]");
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    /**
     * Executes the delete command.
     * Deletes the Task object specified by the number in the input and updates the 
     * save file accordingly.
     * @param input User input to be parsed by Parser to extract the number of the 
     * task to be deleted.
     * @return Validation message and tasks that contain the keyword as a String.
     */
    public String executeDelete(String input) {
        try {
            Task deleted = this.taskList.delete(Parser.getTaskNum(input));
            if (deleted.getIsDone()) {
                this.taskList.decreaseDoneTasks();
            }
            this.storage.delete(deleted.getSaveString());
            return ui.display("deleted:", deleted);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n"
                    + ui.display("format:", "delete [task number]");
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    /**
     * Executes the find command.
     * Returns all Tasks as Strings that contain the specified keyword.
     * @param input User input to be parsed by Parser to extract the keyword to be searched for.
     * @return Validation message as a String.
     */
    public String executeFind(String input) {
        try {
            String keyword = Parser.getKeyword(input);
            String result = "";
            for (int i = 1; i <= this.taskList.getTotalTasks(); i++) {
                if (this.taskList.get(i).description.contains(keyword)) {
                    result += i + ". " + this.taskList.get(i) + "\n  ";
                }
            }
            if (result.equals("")) {
                result += "NONE! >w<";
            }
            return ui.display("found:", result);
        } catch (FroggeException e) {
            return ui.printError(e) + "\n" 
                    + ui.display("format:", "find [keyword]");
        }
    }

    /**
     * Executes the sort command.
     * Sorts the TaskList by the following priority:
     * 1. Unmarked > marked
     * 2. Deadline > Event > Todo
     * 3. Date of deadline/start date
     * Updates the save file accordingly.
     * @return Validation message and the sorted TaskList as a String.
     */
    public String executeSort() {
        try {
            this.taskList.sort();
            this.storage.store();
            return executeList();
        } catch (IOException e) {
            return ui.printError(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello!");
    }

    public String initGreeting() {
        return this.ui.printStart();
    }

    /**
     * Generates a response for the user's chat message according to the command word.
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
        case "find":
            return executeFind(input);
        case "sort":
            return executeSort();
        default:
            return ui.printError(new FroggeException("*ribbit* i don't understand what you're saying! >~<"));
        }
    }
}
