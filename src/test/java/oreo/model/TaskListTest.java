package oreo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Tests task collection operations used by the application. */
class TaskListTest {
    @Test
    void find_matchesDescriptionCaseInsensitivelyAndPreservesOrder() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("buy milk"));
        tasks.add(new Deadline("return BOOK", java.time.LocalDate.of(2019, 6, 6)));

        TaskList matches = tasks.find("book");

        assertEquals(2, matches.size());
        assertEquals("read book", matches.get(0).getDescription());
        assertEquals("return BOOK", matches.get(1).getDescription());
    }
}
