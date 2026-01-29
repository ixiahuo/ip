package frogge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

class Storage {
    private TaskList tasklist;
    private static File saveDirectory = new File(System.getProperty("user.dir") + "/data");
    private static File saveFile = new File(saveDirectory, "frogge.txt");

    Storage(TaskList tasklist) {
        this.tasklist = tasklist;
        try {
            saveDirectory.mkdir();
            saveFile.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
                        this.tasklist.add(new Deadline(savedFields[2], LocalDate.parse(savedFields[3])));
                    } else if (savedFields[0].equals("E")) {
                        this.tasklist.add(new Event(savedFields[2], LocalDate.parse(savedFields[3]), LocalDate.parse(savedFields[4])));
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

    void append(Task task) throws FroggeException {
        try {
            FileWriter saveFileWriter = new FileWriter(saveFile.toString(), true);
            saveFileWriter.write("\n" + task.getSaveString());
            saveFileWriter.close();
        } catch (IOException e) {
            throw new FroggeException("*ribbit* I can't write to your save file right now >~<");
        }
    }

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
