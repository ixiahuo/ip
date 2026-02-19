package frogge;

/**
 * To handle user interactions with the Frogge chat bot.
 */
class Ui {
    /**
     * Prints greeting at the start.
     */
    String printStart() {
        return "i'm frogge! *ribbit*\nwhat can i do for you?";
    }

    /**
     * Prints goodbye at the end.
     */
    String printExit() {
        return "*ribbit* come back soon!";
    }

    /**
     * Prints error message.
     * @param e Exception that was thrown.
     */
    String printError(Exception e) {
        return e.getMessage();
    }

    /**
     * Prints an object with a message, formatted.
     * @param message String to be printed with the object.
     * @param toPrint Object to be printed.
     */
    String display(String message, Object toPrint) {
        return "*ribbit* " + message + "\n  " + toPrint.toString();
    }

    /**
     * Prints a formatted message.
     * @param message String to be printed.
     */
    String display(String message) {
        return "*ribbit* " + message;
    }

    /**
     * Prints a horizontal line.
     */
    void showLine() {
        System.out.println("_________________________________________________________");
    }
}
