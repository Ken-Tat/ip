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
        tasks.add(new Deadline("return BOOK", "2019-06-06"));

        TaskList matches = tasks.find("book");

        assertEquals(2, matches.size());
        assertEquals("read book", matches.get(0).getDescription());
        assertEquals("return BOOK", matches.get(1).getDescription());
    }

    @Test
    void findMerchandise_searchesOnlyMerchandise() {
        TaskList tasks = new TaskList();
        Todo task = new Todo("bishan task");
        task.setMerchandise("4-room flat");
        tasks.add(task);
        tasks.add(new Todo("stamp task"));

        assertEquals(1, tasks.findMerchandise("FLAT").size());
        assertEquals(0, tasks.findMerchandise("bishan").size());
    }
}
