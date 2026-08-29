package oreo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Tests task state changes, date matching, and task-list mutations. */
class TaskTest {
    @Test
    void task_markAndUnmark_updatesStatusIconAndDisplay() {
        Todo task = new Todo("buy milk");

        assertEquals(" ", task.getStatusIcon());
        assertEquals("[T][ ] buy milk", task.toString());

        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
        assertEquals("[T][X] buy milk", task.toString());

        task.markAsNotDone();
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    void deadline_occursOn_matchesOnlyItsCalendarDate() {
        Deadline deadline = new Deadline("return book", "2019-10-15");

        assertTrue(deadline.occursOn(LocalDate.of(2019, 10, 15)));
        assertFalse(deadline.occursOn(LocalDate.of(2019, 10, 16)));
    }

    @Test
    void deadline_freeFormDate_doesNotMatchAnyCalendarDate() {
        Deadline deadline = new Deadline("return book", "next Monday");

        assertFalse(deadline.occursOn(LocalDate.of(2019, 10, 15)));
        assertEquals("[D][ ] return book (by: next Monday)", deadline.toString());
    }

    @Test
    void event_occursOn_matchesStartOrEndDateOnly() {
        Event event = new Event("meeting", "2019-10-15", "2019-10-16");

        assertTrue(event.occursOn(LocalDate.of(2019, 10, 15)));
        assertTrue(event.occursOn(LocalDate.of(2019, 10, 16)));
        assertFalse(event.occursOn(LocalDate.of(2019, 10, 17)));
    }

    @Test
    void taskList_addRemoveAndStream_preserveTaskCollectionBehavior() {
        Todo first = new Todo("first");
        Todo second = new Todo("second");
        TaskList tasks = new TaskList(List.of(first));

        assertEquals(1, tasks.size());
        assertEquals(first, tasks.get(0));
        assertFalse(tasks.isEmpty());

        tasks.add(second);
        assertEquals(List.of("first", "second"),
                tasks.stream().map(Task::getDescription).toList());

        tasks.remove(first);
        assertEquals(1, tasks.size());
        assertEquals(second, tasks.iterator().next());
    }
}
