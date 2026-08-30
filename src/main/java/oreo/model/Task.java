package oreo.model;


/**
 * Represents the common state and behaviour of every task.
 * Specific task types extend this class and add only their own date or time data.
 */
public abstract class Task {
    protected final String description;
    private TaskStatus status;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description the task description
     */
    protected Task(String description) {
        this.description = description;
        this.status = TaskStatus.TODO;
    }

    /**
     * Returns the category used to display this task.
     *
     * @return this task's category
     */
    public abstract TaskType getTaskType();

    /** Returns the task description for persistence and other collaborators. */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the icon used to show this task's completion status.
     *
     * @return {@code X} when the task is done; otherwise a space
     */
    public String getStatusIcon() {
        return status.getIcon();
    }

    /**
     * Marks this task as complete.
     */
    public void markAsDone() {
        status = TaskStatus.DONE;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markAsNotDone() {
        status = TaskStatus.TODO;
    }

    /**
     * Returns the shared status and description part of a task's display text.
     * Subclasses prepend their own type marker and append their date details.
     *
     * @return the completion status and description
     */
    @Override
    public String toString() {
        return "[" + getTaskType().getMarker() + "][" + getStatusIcon() + "] " + description;
    }
}
