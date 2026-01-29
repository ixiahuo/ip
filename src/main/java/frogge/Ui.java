package frogge;

class Ui {
    void printStart() {
        System.out.println("i'm frogge! *ribbit*\nwhat can i do for you?");
    }

    void printExit() {
        System.out.println("*ribbit* come back soon!");
    }

    void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    void display(String message, Object toPrint) {
        System.out.println("*ribbit* " + message + "\n  " + toPrint.toString());
    }

    void display(String message) {
        System.out.println("*ribbit* " + message);
    }

    void showLine() {
        System.out.println("_________________________________________________________");
    }
}
