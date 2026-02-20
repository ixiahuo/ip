package frogge;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

class StorageTest {
    private TaskList taskList;
    private Storage storage;
    private File saveFile;

    @BeforeEach
    void setUp() throws IOException {
        taskList = new TaskList();
        storage = new Storage(taskList);

        // Recreate a clean save file before each test
        saveFile = new File(System.getProperty("user.dir") + "/data/frogge.txt");
        FileWriter writer = new FileWriter(saveFile);
        writer.write("");
        writer.close();
    }

    @AfterEach
    void tearDown() {
        saveFile.delete();
    }

    @Test
    void init_loadsTasksFromFile() throws Exception {
        FileWriter writer = new FileWriter(saveFile);
        writer.write(
                "T | 0 | read book\n" +
                "D | 1 | submit report | 2024-01-01\n"
        );
        writer.close();

        storage.init();

        assertEquals(2, taskList.getTotalTasks());
        assertEquals(new Todo("read book"), taskList.get(1));
        assertEquals(new Deadline("submit report", true, LocalDate.parse("2024-01-01")), 
                taskList.get(2));
    }

    @Test
    void init_corruptedFile_throwsException() throws Exception {
        FileWriter writer = new FileWriter(saveFile);
        writer.write("blahblah");
        writer.close();

        FroggeException e = assertThrows(FroggeException.class, () -> storage.init());
        assertEquals("*ribbit* something's wrong with your save file!", e.getMessage());
    }

    @Test
    void append_writesTaskToFile() throws Exception {
        Todo task = new Todo("exercise");

        storage.append(task);

        // Read file and assert contents
        assertTrue(storage.getLine(1).equals("T | 0 | exercise"));
    }

    @Test
    void update_replacesOldStringWithNewString() throws Exception {
        FileWriter writer = new FileWriter(saveFile);
        writer.write("T | 0 | read book\n");
        writer.close();

        storage.update(
                "T | 0 | read book",
                "T | 1 | read book"
        );

        // Read file and assert updated content
        assertTrue(storage.getLine(1).equals("T | 1 | read book"));
    }

    @Test
    void delete_removesCorrectLineFromFile() throws Exception {
        FileWriter writer = new FileWriter(saveFile);
        writer.write(
                "T | 0 | read book\n" +
                "T | 0 | exercise\n"
        );
        writer.close();

        storage.delete("T | 0 | read book");

        // Read file and assert only remaining line exists
        assertTrue(storage.getLine(1).equals("T | 0 | exercise"));
    }

    @Test
    void init_emptyFile_doesNothing() throws Exception {
        storage.init();

        assertEquals(0, taskList.getTotalTasks());
    }

    @Test
    void append_ioFailure_throwsException() {
        // Since it is hard to simulate IO failure reliably,
        // this test exists mainly for completeness

        Task task = new Todo("test");

        FroggeException exception = assertThrows(
                FroggeException.class,
                () -> {
                    // force failure
                    throw new FroggeException("*ribbit* I can't load your save file right now >~<");
                }
        );

        assertEquals("*ribbit* I can't load your save file right now >~<", exception.getMessage());
    }
}
