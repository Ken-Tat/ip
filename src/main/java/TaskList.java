import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Owns the in-memory collection of tasks and provides the operations used by
 * the command loop.
 */
public class TaskList implements Iterable<Task> {
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task to the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes a task from the list. */
    public void remove(Task task) {
        tasks.remove(task);
    }

    /** Returns the task at the zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    /** Returns whether the list has no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Provides a read-only traversal view for persistence and display. */
    public Stream<Task> stream() {
        return tasks.stream();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
