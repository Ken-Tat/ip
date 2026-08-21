/** Represents a command that Oreo can recognize from user input. */
public enum CommandType {
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    DEADLINE,
    EVENT,
    TODO,
    EMPTY,
    UNKNOWN;

    /** Identifies the command represented by the complete user input. */
    public static CommandType fromInput(String input) {
        if (input.equals("bye")) {
            return BYE;
        } else if (input.equals("list")) {
            return LIST;
        } else if (input.equals("mark") || input.startsWith("mark ")) {
            return MARK;
        } else if (input.equals("unmark") || input.startsWith("unmark ")) {
            return UNMARK;
        } else if (input.equals("delete") || input.startsWith("delete ")) {
            return DELETE;
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            return DEADLINE;
        } else if (input.equals("event") || input.startsWith("event ")) {
            return EVENT;
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            return TODO;
        } else if (input.isEmpty()) {
            return EMPTY;
        }
        return UNKNOWN;
    }
}
