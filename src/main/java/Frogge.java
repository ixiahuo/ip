import java.util.Scanner;
import java.util.Arrays;

public class Frogge {
    private static final String HORIZONTAL_LINE = "________________________________________________";
    private static Task[] taskList = new Task[100];
    private static int numTasks = 0;

    // Add todos to the task list.
    private static void addTodo(String taskName) {
        Todo todo = new Todo(taskName);
        Frogge.taskList[numTasks] = todo;
        Frogge.numTasks++;
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* I've added todo task:\n    " + todo + 
            "\nNow you have " + Frogge.numTasks + " tasks in the list");
        System.out.println(HORIZONTAL_LINE);
    }

    // Add deadlined tasks to the task list.
    private static void addDeadline(String taskName, String deadline) {
        Deadline task = new Deadline(taskName, deadline);
        Frogge.taskList[numTasks] = task;
        Frogge.numTasks++;
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* I've added deadline task:\n    " + task + 
            "\nNow you have " + Frogge.numTasks + " tasks in the list");
        System.out.println(HORIZONTAL_LINE);
    }

    // Add events to the task list.
    private static void addEvent(String taskName, String start, String end) {
        Event task = new Event(taskName, start, end);
        Frogge.taskList[numTasks] = task;
        Frogge.numTasks++;
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* I've added event:\n    " + task + 
            "\nNow you have " + Frogge.numTasks + " tasks in the list");
        System.out.println(HORIZONTAL_LINE);
    }

    // Show the current task list.
    private static void list() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* Here are the tasks in your list:");
        for (int i = 0; i < Frogge.numTasks; i++) {
            System.out.println(i + 1 + ". " + Frogge.taskList[i]);
        }
        System.out.println(HORIZONTAL_LINE);
    }

    // Mark task as done using task number in list.
    private static void mark(int taskNum) {
        Frogge.taskList[taskNum - 1].mark();
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* I've marked this as done!");
        System.out.println(Frogge.taskList[taskNum - 1]);
        System.out.println(HORIZONTAL_LINE);
    }

    // Mark task as not done using task number in list.
    private static void unmark(int taskNum) {
        Frogge.taskList[taskNum - 1].unmark();
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* I've marked this as not done yet!");
        System.out.println(Frogge.taskList[taskNum - 1]);
        System.out.println(HORIZONTAL_LINE);
    }

    // Terminates Frogge.
    private static void exit() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Goodbye! *ribbit*");
        System.out.println(HORIZONTAL_LINE);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello I'm Frogge! *ribbit*\nWhat can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        // wait for user input until bye
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            String[] inputArgs = userInput.split(" ");
            String command = inputArgs[0];
            switch(command) {
                case "list":
                    Frogge.list();
                    break;
                    
                case "mark":
                    Frogge.mark(Integer.parseInt(inputArgs[1]));
                    break;

                case "unmark":
                    Frogge.unmark(Integer.parseInt(inputArgs[1]));
                    break;

                case "todo":
                    try {
                        Frogge.addTodo(Arrays.stream(inputArgs, 1, inputArgs.length)
                            .reduce((x,y) -> x + " " + y)
                            .orElseThrow(() -> new FroggeException("*ribbit* todo needs a task description! >:(")));
                    } catch (FroggeException e) {
                        System.out.println(HORIZONTAL_LINE);
                        System.out.println(e.getMessage());
                        System.out.println(HORIZONTAL_LINE);
                    }
                    break;

                case "deadline":
                    try {
                        String desc = Arrays.stream(inputArgs, 1, inputArgs.length)
                            .takeWhile(x -> !x.equals("/by"))
                            .reduce((x,y) -> x + " " + y)
                            .orElse("");
                        if (desc.equals("")) {
                            throw new FroggeException("*ribbit* deadline needs a task description! >:(");
                        }
                        if (userInput.split("/by ").length <= 1) {
                            throw new FroggeException("*ribbit* deadline needs a deadline date! >:(");
                        }
                        String date = userInput.split("/by ")[1];
                        Frogge.addDeadline(desc, date);
                    } catch (FroggeException e) {
                        System.out.println(HORIZONTAL_LINE);
                        System.out.println(e.getMessage());
                        System.out.println(HORIZONTAL_LINE);
                    }
                    break;

                case "event":
                    try {
                        String desc = Arrays.stream(inputArgs, 1, inputArgs.length)
                            .takeWhile(x -> !x.equals("/from") && !x.equals("/to"))
                            .reduce((x,y) -> x + " " + y)
                            .orElse("");
                        if (desc.equals("")) {
                            throw new FroggeException("*ribbit* event needs a description! >:(");
                        }
                        if (userInput.split("/from ").length <= 1) {
                            throw new FroggeException("*ribbit* event needs a start time! >:(");
                        }
                        if (userInput.split("/to ").length <= 1) {
                            throw new FroggeException("*ribbit* event needs an end time! >:(");
                        }
                        String start = userInput.split("/from ")[1].split(" /to ")[0];
                        String end = userInput.split("/to ")[1];
                        Frogge.addEvent(Arrays.stream(inputArgs, 1, inputArgs.length)
                            .takeWhile(x -> !x.equals("/from"))
                            .reduce((x,y) -> x + " " + y)
                            .orElse(""), start, end);
                    } catch (FroggeException e) {
                        System.out.println(HORIZONTAL_LINE);
                        System.out.println(e.getMessage());
                        System.out.println(HORIZONTAL_LINE);
                    }
                    break;
                default:
                    try {
                        throw new FroggeException("*ribbit* i don't understand you >~<");
                    } catch (FroggeException e) {
                        System.out.println(HORIZONTAL_LINE);
                        System.out.println(e.getMessage());
                        System.out.println(HORIZONTAL_LINE);
                    };
            }
            userInput = sc.nextLine();
        }

        // exits with user input "bye"
        exit();
        sc.close();
    }
}
