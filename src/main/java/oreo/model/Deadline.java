package oreo.model;

import oreo.core.DateTimeParser;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Represents a task that should be completed by a specified date or time.
 */
public class Deadline extends Task {
    private final LocalDateTime byDateTime;
    private final String byText;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description The task description.
     * @param by The deadline text entered by the user.
     */
    public Deadline(String description, String by) {
        super(description);
        this.byDateTime = DateTimeParser.parse(by);
        this.byText = by;
    }

    /** Returns the deadline task category. */
    @Override
    public TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    /** Returns the deadline text for persistence. */
    public String getBy() {
        return byText;
    }

    /** Returns the parsed deadline, or {@code null} for legacy free-form text. */
    public LocalDateTime getByDateTime() {
        return byDateTime;
    }

    /** Returns whether this parsed deadline falls on the supplied date. */
    public boolean occursOn(LocalDate date) {
        return byDateTime != null && byDateTime.toLocalDate().equals(date);
    }

    /** Returns the formatted deadline task text. */
    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeParser.format(byDateTime, byText) + ")";
    }
}
