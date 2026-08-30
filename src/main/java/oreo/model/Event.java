package oreo.model;

import oreo.core.DateTimeParser;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Represents a task that starts and ends at specified dates or times.
 */
public class Event extends Task {
    private final LocalDateTime fromDateTime;
    private final LocalDateTime toDateTime;
    private final String fromText;
    private final String toText;

    /**
     * Creates an incomplete event task.
     *
     * @param description The task description.
     * @param from The event start text entered by the user.
     * @param to The event end text entered by the user.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.fromDateTime = DateTimeParser.parse(from);
        this.toDateTime = DateTimeParser.parse(to);
        this.fromText = from;
        this.toText = to;
    }

    /** Returns the event task category. */
    @Override
    public TaskType getTaskType() {
        return TaskType.EVENT;
    }

    /** Returns the event start text for persistence. */
    public String getFrom() {
        return fromText;
    }

    /** Returns the event end text for persistence. */
    public String getTo() {
        return toText;
    }

    /** Returns whether this event starts or ends on the supplied date. */
    public boolean occursOn(LocalDate date) {
        return (fromDateTime != null && fromDateTime.toLocalDate().equals(date))
                || (toDateTime != null && toDateTime.toLocalDate().equals(date));
    }

    /** Returns the formatted event task text. */
    @Override
    public String toString() {
        return super.toString() + " (from: " + DateTimeParser.format(fromDateTime, fromText)
                + " to: " + DateTimeParser.format(toDateTime, toText) + ")";
    }
}
