import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Frogge {
    private static final String HORIZONTAL_LINE = "________________________________________________";
    private static ArrayList<Task> taskList = new ArrayList<Task>(100);
    private static int numTasks = 0;
    private static File saveDirectory = new File(System.getProperty("user.dir") + "../../../../data");
    private static File saveFile = new File(Frogge.saveDirectory, "frogge.txt");

    // Add todos to the task list.
    private static Todo addTodo(String userInput) throws FroggeException{
        String taskName = Todo.getDescription(userInput);
        Todo todo = new Todo(taskName);
        Frogge.taskList.add(todo);
        Frogge.numTasks++;
        try {
            FileWriter saveFileWriter = new FileWriter(System.getProperty("user.dir") + 
                "../../../../data/frogge.txt", true);
            saveFileWriter.write(todo.getSaveString());
            saveFileWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* I can't write to your save file right now >~<");
        }
        return todo;
    }

    // Add deadlined tasks to the task list.
    private static Deadline addDeadline(String userInput) throws FroggeException{
        String desc = Deadline.getDescription(userInput);
        String deadline = Deadline.getDeadline(userInput);
        Deadline task = new Deadline(desc, deadline);
        Frogge.taskList.add(task);
        Frogge.numTasks++;
        try {
            FileWriter saveFileWriter = new FileWriter(System.getProperty("user.dir") + 
                "../../../../data/frogge.txt", true);
            saveFileWriter.write(task.getSaveString());
            saveFileWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* I can't write to your save file right now >~<");
        }
        return task;
    }

    // Add events to the task list.
    private static Event addEvent(String userInput) throws FroggeException {
        String desc = Event.getDescription(userInput);
        String start = Event.getStart(userInput);
        String end = Event.getEnd(userInput);
        Event task = new Event(desc, start, end);
        Frogge.taskList.add(task);
        Frogge.numTasks++;
        try {
            FileWriter saveFileWriter = new FileWriter(System.getProperty("user.dir") + 
                "../../../../data/frogge.txt", true);
            saveFileWriter.write(task.getSaveString());
            saveFileWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* I can't write to your save file right now >~<");
        }        
        return task;
    }

    // Show the current task list.
    private static void list() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("*ribbit* Here are the tasks in your list:");
        for (int i = 0; i < Frogge.numTasks; i++) {
            System.out.println(i + 1 + ". " + Frogge.taskList.get(i));
        }
        System.out.println(HORIZONTAL_LINE);
    }

    // Mark task as done using task number in list.
    private static void mark(int taskNum) throws FroggeException {
        String oldSaveString = Frogge.taskList.get(taskNum - 1).getSaveString();
        System.out.println(oldSaveString);
        Frogge.taskList.get(taskNum - 1).mark();
        String newSaveString = Frogge.taskList.get(taskNum - 1).getSaveString();
        System.out.println(newSaveString);
        // input the file content to the StringBuffer "input"
        try {
            BufferedReader file = new BufferedReader(new FileReader(Frogge.saveFile.toString()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();
            inputStr = inputStr.replace(oldSaveString, newSaveString); 
            FileWriter saveWriter = new FileWriter(Frogge.saveFile.toString());
            saveWriter.write(inputStr);
            saveWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* There's a problem reading/writing to your save file.");
        }
    }

    // Mark task as not done using task number in list.
    private static void unmark(int taskNum) throws FroggeException {
        String oldSaveString = Frogge.taskList.get(taskNum - 1).getSaveString();
        System.out.println(oldSaveString);
        Frogge.taskList.get(taskNum - 1).unmark();
        String newSaveString = Frogge.taskList.get(taskNum - 1).getSaveString();
        System.out.println(newSaveString);
        // input the file content to the StringBuffer "input"
        try {
            BufferedReader file = new BufferedReader(new FileReader(Frogge.saveFile.toString()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();
            inputStr = inputStr.replace(oldSaveString, newSaveString); 
            FileWriter saveWriter = new FileWriter(Frogge.saveFile.toString());
            saveWriter.write(inputStr);
            saveWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* There's a problem reading/writing to your save file.");
        }
    }

    // Terminates Frogge.
    private static void exit() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Goodbye! *ribbit*");
        System.out.println(HORIZONTAL_LINE);
    }
    public static void main(String[] args) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello I'm Frogge! *ribbit*\nWhat can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        // Read from save file.
        try {
            Frogge.saveDirectory.mkdir();
            Frogge.saveFile.createNewFile();
            Scanner fileScanner = new Scanner(saveFile);
            int i = 0;
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] savedFields = data.split(" \\| ");
                if (savedFields[0].equals("T")) {
                    Frogge.taskList.add(new Todo(savedFields[2]));
                } else if (savedFields[0].equals("D")) {
                    Frogge.taskList.add(new Deadline(savedFields[2], savedFields[3]));
                } else if (savedFields[0].equals("E")) {
                    Frogge.taskList.add(new Event(savedFields[2], savedFields[3], savedFields[4]));
                }
                if (savedFields[1].equals("1")) {
                    Frogge.taskList.get(i).mark();
                }
                i++;
                Frogge.numTasks++;
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("*ribbit* Sorry, I can't create/find your save file right now! >~<\n" +
                    "It should be located in ../../../data/frogge.txt");
            return;
        }

        // Start reading inputs
        Scanner inputScanner = new Scanner(System.in);

        // wait for user input until bye
        String userInput = inputScanner.nextLine();
        while (!userInput.equals("bye")) {
            String[] inputArgs = userInput.split(" ");
            String command = inputArgs[0];
            switch(command) {
            case "list":
                Frogge.list();
                break;
                
            case "mark":
                try {
                    if (inputArgs.length <= 1) {
                        throw new FroggeException("*ribbit* what task do you want to mark as done?");
                    }
                    int taskNum = Integer.parseInt(inputArgs[1]);
                    if (taskNum > Frogge.numTasks || taskNum <= 0) {
                        throw new FroggeException("*ribbit* i can't find that task! >~<");
                    }
                    Frogge.mark(taskNum);
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("*ribbit* I've marked this as done!");
                    System.out.println(Frogge.taskList.get(taskNum - 1));
                    System.out.println(HORIZONTAL_LINE);
                } catch (FroggeException e) {
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println(e.getMessage());
                    System.out.println(HORIZONTAL_LINE);
                }
                break;

            case "unmark":
                try {
                    if (inputArgs.length <= 1) {
                        throw new FroggeException("*ribbit* what task do you want to mark as not done?");
                    }
                    int taskNum = Integer.parseInt(inputArgs[1]);
                    if (taskNum > Frogge.numTasks || taskNum <= 0) {
                        throw new FroggeException("*ribbit* i can't find that task! >~<");
                    }
                    Frogge.unmark(taskNum);
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("*ribbit* I've marked this as not yet done!");
                    System.out.println(Frogge.taskList.get(taskNum - 1));
                    System.out.println(HORIZONTAL_LINE);
                } catch (FroggeException e) {
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println(e.getMessage());
                    System.out.println(HORIZONTAL_LINE);
                }
                break;

            case "todo":
                try {
                    Todo todo = Frogge.addTodo(userInput);
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("*ribbit* I've added todo task:\n    " + todo + 
                            "\nNow you have " + Frogge.numTasks + " tasks in the list");
                    System.out.println(HORIZONTAL_LINE);
                } catch (FroggeException e) {
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println(e.getMessage());
                    System.out.println(HORIZONTAL_LINE);
                }
                break;

            case "deadline":
                try {
                    Deadline task = Frogge.addDeadline(userInput);
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("*ribbit* I've added deadline task:\n    " + task + 
                            "\nNow you have " + Frogge.numTasks + " tasks in the list");
                    System.out.println(HORIZONTAL_LINE);
                } catch (FroggeException e) {
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println(e.getMessage());
                    System.out.println(HORIZONTAL_LINE);
                }
                break;

            case "event":
                try {
                    Event task = Frogge.addEvent(userInput);
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("*ribbit* I've added event:\n    " + task + 
                        "\nNow you have " + Frogge.numTasks + " tasks in the list");
                    System.out.println(HORIZONTAL_LINE);
                } catch (FroggeException e) {
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println(e.getMessage());
                    System.out.println(HORIZONTAL_LINE);
                }
                break;

            case "delete":
                try {
                    if (inputArgs.length <= 1) {
                        throw new FroggeException("*ribbit* what task do you want to delete?");
                    }
                    int taskNum = Integer.parseInt(inputArgs[1]);
                    if (taskNum > Frogge.numTasks || taskNum <= 0) {
                        throw new FroggeException("*ribbit* i can't find that task! >~<");
                    }
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("*ribbit* i've removed this task:\n    " + Frogge.taskList.get(taskNum - 1));
                    Frogge.taskList.remove(taskNum - 1);
                    Frogge.numTasks--;
                    System.out.println("You have " + Frogge.numTasks + " tasks left");
                    System.out.println(HORIZONTAL_LINE);
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
            userInput = inputScanner.nextLine();
        }

        // exits with user input "bye"
        exit();
        inputScanner.close();
    }
}
