/**
 * Represents one task in the task list.
 * A task stores its type and any optional date or time text directly, so the
 * program does not need a class hierarchy for to-dos, deadlines, and events.
 */
public class Task {
    /** The supported kinds of tasks and their display prefixes. */
    public enum Type {
        TODO("T"),
        DEADLINE("D"),
        EVENT("E");

        private final String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        /**
         * Returns the prefix shown for this task type.
         *
         * @return a one-letter task prefix
         */
        public String getPrefix() {
            return prefix;
        }
    }

    private final String description;
    private final Type type;
    private final String by;
    private final String from;
    private final String to;
    private boolean isDone;

    /**
     * Creates an incomplete task. Use the factory methods to create a task of
     * one of the supported types.
     *
     * @param description the task description
     * @param type the task kind
     * @param by deadline text, or {@code null} when this is not a deadline
     * @param from event start text, or {@code null} when this is not an event
     * @param to event end text, or {@code null} when this is not an event
     */
    private Task(String description, Type type, String by, String from, String to) {
        this.description = description;
        this.type = type;
        this.by = by;
        this.from = from;
        this.to = to;
        this.isDone = false;
    }

    /** Creates an incomplete to-do with no date or time. */
    public static Task todo(String description) {
        return new Task(description, Type.TODO, null, null, null);
    }

    /** Creates an incomplete deadline task. */
    public static Task deadline(String description, String by) {
        return new Task(description, Type.DEADLINE, by, null, null);
    }

    /** Creates an incomplete event task. */
    public static Task event(String description, String from, String to) {
        return new Task(description, Type.EVENT, null, from, to);
    }

    /**
     * Returns the icon used to show this task's completion status.
     *
     * @return {@code X} when the task is done; otherwise a space
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks this task as complete.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns this task in the format shown to users. Date and time values are
     * displayed as entered; this version does not parse them as real dates.
     *
     * @return the task type, status, and description
     */
    @Override
    public String toString() {
        String taskText = "[" + type.getPrefix() + "][" + getStatusIcon() + "] " + description;
        if (type == Type.DEADLINE) {
            return taskText + " (by: " + by + ")";
        }
        if (type == Type.EVENT) {
            return taskText + " (from: " + from + " to: " + to + ")";
        }
        return taskText;
    }
}
