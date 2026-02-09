package frogge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Represents the storage media that the tasks are saved to and loaded from.
 * Handles reading and writing from the save file.
 */
class Storage {
    private TaskList tasklist;
    private static File saveDirectory = new File(System.getProperty("user.dir") + "/data");
    private static File saveFile = new File(saveDirectory, "frogge.txt");

    /**
     * Constructor for Storage.
     * Creates data/frogge.txt if file does not exist.
     * @param tasklist TaskList to be initialised.
     */
    Storage(TaskList tasklist) {
        this.tasklist = tasklist;
        try {
            saveDirectory.mkdir();
            saveFile.createNewFile();
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
            Scanner fileScanner = new Scanner(saveFile);
            int numTasks = 0;
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                if (!data.equals("")) {
                    String[] savedFields = data.split(" \\| ");
                    if (savedFields[0].equals("T")) {
                        this.tasklist.add(new Todo(savedFields[2]));
                    } else if (savedFields[0].equals("D")) {
                        this.tasklist.add(new Deadline(savedFields[2],
                                LocalDate.parse(savedFields[3])));
                    } else if (savedFields[0].equals("E")) {
                        this.tasklist.add(new Event(savedFields[2], 
                                LocalDate.parse(savedFields[3]), 
                                LocalDate.parse(savedFields[4])));
                    }
                    numTasks++;
                    if (savedFields[1].equals("1")) {
                        this.tasklist.mark(numTasks);
                    }
                }
            }
            fileScanner.close();
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
        Scanner fileScanner = new Scanner(saveFile);
        String line = "";
        for (int i = 0; i < lineNum; i++) {
            line = fileScanner.nextLine();
        }
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
            FileWriter saveFileWriter = new FileWriter(saveFile.toString(), true);
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
            BufferedReader file = new BufferedReader(new FileReader(saveFile.toString()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();
            inputStr = inputStr.replace(oldString, newString); 
            FileWriter saveWriter = new FileWriter(saveFile.toString());
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
            BufferedReader file = new BufferedReader(new FileReader(saveFile.toString()));
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
            FileWriter saveWriter = new FileWriter(saveFile.toString());
            saveWriter.write(inputStr);
            saveWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* There's a problem reading/writing to your save file.");
        }
    }
}
