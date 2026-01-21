import java.util.Scanner;

public class Frogge {
    private static final String HORIZONTAL_LINE = "_____________________________________________";
    private static String[] taskList = new String[100];
    private static int numTasks = 0;

    private static void addList(String task) {
        Frogge.taskList[numTasks] = task;
        Frogge.numTasks++;
        System.out.println(HORIZONTAL_LINE);
        System.out.println("added: " + task);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void list() {
        System.out.println(HORIZONTAL_LINE);
        for (int i = 0; i < Frogge.numTasks; i++) {
            System.out.println(i + 1 + ". " + Frogge.taskList[i]);
        }
        System.out.println(HORIZONTAL_LINE);
    }

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
            switch(userInput) {
                case "list":
                    Frogge.list();
                    break;
                default:
                    Frogge.addList(userInput);
            }
            userInput = sc.nextLine();
        }

        // exits with user input "bye"
        exit();
        sc.close();
    }
}
