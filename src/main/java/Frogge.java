import java.util.Scanner;

public class Frogge {
    static final String HORIZONTAL_LINE = "_____________________________________________";

    private static void echo(String userInput) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(userInput);
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
            echo(userInput);
            userInput = sc.nextLine();
        }

        // exits with user input "bye"
        exit();
    }
}
