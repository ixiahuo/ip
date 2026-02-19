package frogge;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Handles parsing and interpretation of user input.
 */
class Parser {

    /**
     * Removes additional white space from the user input so additional white space
     * will not invalidate a command.
     * @param userInput String to be parsed.
     * @return String object without additional white space.
     */
    static String removeWhiteSpace(String userInput) {
        return userInput.trim().replaceAll(" +", " ");
    }

    /**
     * Extracts the command string from the given user input.
     * @param userInput String to be parsed.
     * @return String object representing the command to be executed in Frogge.
     */
    static String getCommand(String userInput) {
        return userInput.split(" ")[0];
    }

    /**
     * Extracts the number of the specified task in the TaskList from the given user input.
     * Converts the number from String to int.
     * @param userInput String to be parsed.
     * @return int object representing the task number in the TaskList. 
     * @throws FroggeException If no task number is provided or if the task number is invalid or out of bounds.
     */
    static String getKeyword(String userInput) throws FroggeException {
        return Arrays.stream(removeWhiteSpace(userInput).split(" "))
                .skip(1)
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* i need a keyword!"));
    }

    static int getTaskNum(String userInput) throws FroggeException {
        try {
            return Integer.parseInt(removeWhiteSpace(userInput).split(" ")[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* what's the task number!");
        } catch (NumberFormatException e) {
            throw new FroggeException("*ribbit* that task doesn't exist!");
        }
    }

    /**
     * Extracts the description for a Todo object when the todo command is called,
     * from the given user input.
     * @param userInput String to be parsed.
     * @return String object representing the description.
     * @throws FroggeException If no description is provided.
     */
    static String getTodoDescription(String userInput) throws FroggeException {
        return Arrays.stream(removeWhiteSpace(userInput).split(" "))
                .skip(1)
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* i need a description!"));
    }

    /**
     * Extracts the description for a Deadline object when the deadline command is called,
     * from the given user input.
     * @param userInput String to be parsed.
     * @return String object representing the description.
     * @throws FroggeException If no description is provided.
     */
    static String getDeadlineDescription(String userInput) throws FroggeException {
        return Arrays.stream(removeWhiteSpace(userInput).split(" "))
                .skip(1)
                .takeWhile(x -> !x.equals("/by"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* i need a description!"));
    }

    /**
     * Extracts the deadline for a Deadline object as a LocalDate object, from the given user input.
     * Parses the string after "/by" in the format of [yyyy-mm-dd] and converts the String 
     * representation of the date to a LocalDate.
     * @param userInput String to be parsed.
     * @return LocalDate representation of the deadline date.
     * @throws FroggeException If no date in the correct format is provided.
     */
    static LocalDate getDeadline(String userInput) throws FroggeException {
        try {
            return LocalDate.parse(removeWhiteSpace(userInput).split("/by ")[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new FroggeException("*ribbit* i need a deadline!");
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* choose a valid date! >:(");
        }
    }

    /**
     * Extracts the description for an Event object when the event command is called,
     * from the given user input.
     * @param userInput String to be parsed.
     * @return String object representing the description.
     * @throws FroggeException If no description is provided.
     */
    static String getEventDescription(String userInput) throws FroggeException {
        return Arrays.stream(removeWhiteSpace(userInput).split(" "))
            .skip(1)
            .takeWhile(x -> !x.equals("/from") && !x.equals("/to"))
            .reduce((x,y) -> x + " " + y)
            .orElseThrow(() -> new FroggeException("*ribbit* i need a description!"));
    }

    /**
     * Extracts the start date for an Event object as a LocalDate object, from the given user input.
     * Parses the string after "/from" in the format of [yyyy-mm-dd] and converts the String 
     * representation of the date to a LocalDate.
     * @param userInput String to be parsed.
     * @return LocalDate object representing the start date.
     * @throws FroggeException If no start date is provided.
     */
    static LocalDate getStart(String userInput) throws FroggeException {
        String[] args = removeWhiteSpace(userInput).split(" ");
        int fromIndex = Arrays.stream(args)
                .takeWhile(x -> !x.equals("/from"))
                .map(x -> 1)
                .reduce(0, (x,y) -> x + y);

        assert(fromIndex >= 1 && fromIndex <= args.length);        
        
        if (fromIndex == args.length) {
            throw new FroggeException("*ribbit* i need a start time!");
        }

        try {
            return LocalDate.parse(Arrays.stream(args, fromIndex, args.length)
                    .skip(1)
                    .takeWhile(x -> !x.equals("/to"))
                    .reduce((x,y) -> x + " " + y)
                    .orElseThrow(() -> new FroggeException("*ribbit* i need a start time!")));
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* choose a valid date >:(");
        }
    }

    /**
     * Extracts the end date for an Event object as a LocalDate object, from the given user input.
     * Parses the string after "/to" in the format of [yyyy-mm-dd] and converts the String 
     * representation of the date to a LocalDate.
     * @param userInput String to be parsed.
     * @return LocalDate object representing the end date.
     * @throws FroggeException If no end date is provided.
     */
    static LocalDate getEnd(String userInput) throws FroggeException {
        String[] args = removeWhiteSpace(userInput).split(" ");
        int toIndex = Arrays.stream(args)
                .takeWhile(x -> !x.equals("/to"))
                .map(x -> 1)
                .reduce(0, (x,y) -> x + y);

        assert(toIndex >= 1 && toIndex <= args.length);

        if (toIndex == args.length) {
            throw new FroggeException("*ribbit* i need an end time!");
        }
        
        try {
            return LocalDate.parse(Arrays.stream(args, toIndex, args.length)
                .skip(1)
                .takeWhile(x -> !x.equals("/from"))
                .reduce((x,y) -> x + " " + y)
                .orElseThrow(() -> new FroggeException("*ribbit* i need an end time!")));
        } catch (DateTimeException e) {
            throw new FroggeException("*ribbit* choose a valid date >:(");
        }
    }

}
