package frogge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void add_addsTaskAndIncrementsCount() {
        Task task = new Todo("read book");

        taskList.add(task);

        assertEquals(1, taskList.getTotalTasks());
        assertEquals(new Todo("read book"), taskList.get(1));
    }

    @Test
    void get_returnsCorrectTask() {
        Task task = new Todo("do homework");
        taskList.add(task);

        Task result = taskList.get(1);

        assertEquals(new Todo("do homework"), result);
    }

    @Test
    void addTodo_returnsTodoAndAddsToList() throws FroggeException {
        Todo todo = taskList.addTodo("todo read book");

        assertNotNull(todo);
        assertEquals(1, taskList.getTotalTasks());
        assertEquals(new Todo("read book"), taskList.get(1));
    }

    @Test
    void addDeadline_returnsDeadlineAndAddsToList() throws FroggeException {
        Deadline deadline = taskList.addDeadline("deadline submit report /by 2024-01-01");

        assertNotNull(deadline);
        assertEquals(1, taskList.getTotalTasks());
        assertEquals(new Deadline("submit report", LocalDate.parse("2024-01-01")), taskList.get(1));
    }

    @Test
    void addEvent_returnsEventAndAddsToList() throws FroggeException {
        Event event = taskList.addEvent("event conference /from 2024-01-01 /to 2024-01-03");

        assertNotNull(event);
        assertEquals(1, taskList.getTotalTasks());
        assertEquals(new Event("conference", 
                LocalDate.parse("2024-01-01"), 
                LocalDate.parse("2024-01-03")), taskList.get(1));
    }

    @Test
    void mark_marksTask() throws FroggeException {
        Task task = new Todo("exercise");
        taskList.add(task);
        Task marked = taskList.mark(1);
        assertEquals(new Todo("exercise", true), marked);
    }

    @Test
    void unmark_unmarksTask() throws FroggeException {
        Task task = new Todo("exercise");
        task.mark();
        taskList.add(task);
        Task unmarked = taskList.unmark(1);
        assertEquals(new Todo("exercise"), unmarked);
    }

    @Test
    void delete_removesCorrectTaskAndDecrementsCount() throws FroggeException {
        Task task1 = new Todo("task 1");
        Task task2 = new Todo("task 2");

        taskList.add(task1);
        taskList.add(task2);

        Task deleted = taskList.delete(1);

        assertEquals(new Todo("task 1"), deleted);
        assertEquals(1, taskList.getTotalTasks());
        assertEquals(new Todo("task 2"), taskList.get(1));
    }

    @Test
    void delete_invalidIndex_throwsException() {
        FroggeException exception = assertThrows(
                FroggeException.class,
                () -> taskList.delete(1)
        );

        assertEquals("*ribbit* this task doesn't exist.", exception.getMessage());
    }

    @Test
    void mark_invalidIndex_throwsException() {
        FroggeException exception = assertThrows(
                FroggeException.class,
                () -> taskList.mark(1)
        );

        assertEquals("*ribbit* this task doesn't exist.", exception.getMessage());
    }
}
