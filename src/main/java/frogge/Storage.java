package frogge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Represents the storage media that the tasks are saved to and loaded from.
 * Handles reading and writing from the save file.
 */
class Storage {
    private TaskList taskList;
    private final static File SAVE_DIRECTORY = new File(System.getProperty("user.dir") + "/data");
    private final static File SAVE_FILE = new File(SAVE_DIRECTORY, "frogge.txt");

    /**
     * Constructor for Storage.
     * Creates data/frogge.txt if file does not exist.
     * @param tasklist TaskList to be initialised.
     */
    Storage(TaskList taskList) {
        this.taskList = taskList;
        try {
            SAVE_DIRECTORY.mkdir();
            SAVE_FILE.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initialises the TaskList.
     * Parses each line in the save file and converts it into the corresponding 
     * Task object before adding to the TaskList.
     * @throws FroggeException If the save file is missing or corrupted.
     */
    void init() throws FroggeException {
        try {
            Scanner fileScanner = new Scanner(SAVE_FILE);
            int numTasks = 0;
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                if (!data.equals("")) {
                    String[] savedFields = data.split(" \\| ");
                    if (savedFields[0].equals("T")) {
                        this.taskList.add(new Todo(savedFields[2]));
                    } else if (savedFields[0].equals("D")) {
                        assert(savedFields.length == 4);
                        this.taskList.add(new Deadline(savedFields[2],
                                LocalDate.parse(savedFields[3])));
                    } else if (savedFields[0].equals("E")) {
                        assert(savedFields.length == 5);
                        this.taskList.add(new Event(savedFields[2], 
                                LocalDate.parse(savedFields[3]), 
                                LocalDate.parse(savedFields[4])));
                    }
                    numTasks++;
                    if (savedFields[1].equals("1")) {
                        this.taskList.mark(numTasks);
                    }
                }
            }
            fileScanner.close();
            assert(this.taskList.numTasks == numTasks);
        } catch (IOException e) {
            throw new FroggeException("*ribbit* I can't load your save file right now >~<");
        }
    }

    /**
     * Gets the specified line in the save file.
     * @param lineNum Position number of the specified line, starting from 1.
     * @return String object representing the line text.
     * @throws Exception If there is an error reading the save file. 
     */
    String getLine(int lineNum) throws Exception {
        Scanner fileScanner = new Scanner(SAVE_FILE);
        String line = Stream.<Integer>iterate(0, i -> i + 1)
                .limit(lineNum)
                .map(i -> fileScanner.nextLine())
                .reduce("", (x,y) -> y);
        fileScanner.close();
        return line;
    }

    /**
     * Writes a new Task to the end of the save file.
     * Task is represented in the format of [type] | [isDone] | [name] | [additional fields]
     * E.g. T | 0 | borrow book
     * @param task Task to be converted to a save string and written.
     * @throws FroggeException If the save file is missing.
     */
    void append(Task task) throws FroggeException {
        try {
            FileWriter saveFileWriter = new FileWriter(SAVE_FILE.toString(), true);
            saveFileWriter.write(task.getSaveString());
            saveFileWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* I can't write to your save file right now >~<");
        }
    }

    /**
     * Overwrites a specific line in the save file.
     * @param oldString Line to be overwritten.
     * @param newString Line to overwrite with.
     * @throws FroggeException If save file is missing.
     */
    void update(String oldString, String newString) throws FroggeException {
        try {
            BufferedReader file = new BufferedReader(new FileReader(SAVE_FILE.toString()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();
            inputStr = inputStr.replace(oldString, newString); 
            FileWriter saveWriter = new FileWriter(SAVE_FILE.toString());
            saveWriter.write(inputStr);
            saveWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* There's a problem reading/writing to your save file.");
        }
    }

    /**
     * Deletes a specific line in the save file.
     * @param toDelete String object representing the line to be deleted.
     * @throws FroggeException If save file is missing.
     */
    void delete(String toDelete) throws FroggeException {
        try {
            BufferedReader file = new BufferedReader(new FileReader(SAVE_FILE.toString()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                if (!line.equals(toDelete)) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
            }
            file.close();
            String inputStr = inputBuffer.toString();
            FileWriter saveWriter = new FileWriter(SAVE_FILE.toString());
            saveWriter.write(inputStr);
            saveWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* There's a problem reading/writing to your save file.");
        }
    }
}
