package frogge;

/**
 * To handle user interactions with the Frogge chat bot.
 */
class Ui {
    /**
     * Prints greeting at the start.
     */
    void printStart() {
        System.out.println("i'm frogge! *ribbit*\nwhat can i do for you?");
    }

    /**
     * Prints goodbye at the end.
     */
    void printExit() {
        System.out.println("*ribbit* come back soon!");
    }

    /**
     * Prints error message.
     * @param e Exception that was thrown.
     */
    void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    /**
     * Prints an object with a message, formatted.
     * @param message String to be printed with the object.
     * @param toPrint Object to be printed.
     */
    void display(String message, Object toPrint) {
        System.out.println("*ribbit* " + message + "\n  " + toPrint.toString());
    }

    /**
     * Prints a formatted message.
     * @param message String to be printed.
     */
    void display(String message) {
        System.out.println("*ribbit* " + message);
    }

    /**
     * Prints a horizontal line.
     */
    void showLine() {
        System.out.println("_________________________________________________________");
    }
}
