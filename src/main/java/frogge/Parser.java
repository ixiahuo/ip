package frogge;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;

class Parser {

    static String getCommand(String userInput) {
        return userInput.split(" ")[0];
    }

    static int getTaskNum(String userInput) throws FroggeException {
        try {
            return Integer.parseInt(userInput.split(" ")[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* what's the task number!");
        } catch (NumberFormatException e) {
            throw new FroggeException("*ribbit* that task doesn't exist!");
        }
    }

    static String getTodoDescription(String userInput) throws FroggeException {
        return Arrays.stream(userInput.split(" "))
                .skip(1)
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need a description! Follow the following format for todo:\n" + 
                "   todo [description]"));
    }

    static String getDeadlineDescription(String userInput) throws FroggeException {
        return Arrays.stream(userInput.split(" "))
                .skip(1)
                .takeWhile(x -> !x.equals("/by"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need a description! " + 
                    "Follow the following format for deadline:\n" + 
                    "   deadline [description] /by [deadline]"));
    }

    static LocalDate getDeadline(String userInput) throws FroggeException {
        try {
            return LocalDate.parse(userInput.split("/by ")[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* I need a deadline! " +
                    "Follow the following format for deadline:\n" + 
                    "   deadline [description] /by [yyyy-mm-dd]");
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* Choose a valid date! >:(");
        }
    }

    static String getEventDescription(String userInput) throws FroggeException {
        return Arrays.stream(userInput.split(" "))
            .skip(1)
            .takeWhile(x -> !x.equals("/from") && !x.equals("/to"))
            .reduce((x,y) -> x + " " + y)
            .orElseThrow(() -> new FroggeException("*ribbit* I need a description! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]"));
    }

    static LocalDate getStart(String userInput) throws FroggeException {
        String[] args = userInput.split(" ");
        int fromIndex;
        for (fromIndex = 0; fromIndex < args.length; fromIndex++) {
            if (args[fromIndex].equals("/from")) {
                break;
            }
        }
        if (fromIndex == args.length) {
            throw new FroggeException("*ribbit* I need a start time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
        }
        try {
            return LocalDate.parse(Arrays.stream(args, fromIndex, args.length)
                    .skip(1)
                    .takeWhile(x -> !x.equals("/to"))
                    .reduce((x,y) -> x + " " + y)
                    .orElseThrow(() -> new FroggeException("*ribbit* I need a start time! " + 
                        "Follow the following format for event:\n" + 
                        "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]")));
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* Choose a valid date >:(");
        }
    }

    static LocalDate getEnd(String userInput) throws FroggeException {
        String[] args = userInput.split(" ");
        int toIndex;
        for (toIndex = 0; toIndex < args.length; toIndex++) {
            if (args[toIndex].equals("/to")) {
                break;
            }
        }
        if (toIndex == args.length) {
            throw new FroggeException("*ribbit* I need an end time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]");
        }
        try {
            return LocalDate.parse(Arrays.stream(args, toIndex, args.length)
                .skip(1)
                .takeWhile(x -> !x.equals("/from"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* I need an end time! " + 
                    "Follow the following format for event:\n" + 
                    "   event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]")));
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* Choose a valid date >:(");
        }
    }

}
