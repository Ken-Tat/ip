/** Converts raw user input into the command classification used by Oreo. */
public class Parser {
    /** Identifies the command represented by the supplied input. */
    public CommandType parse(String input) throws OreoException {
        return CommandType.fromInput(input);
    }

    /** Returns the argument following a command keyword, or an empty string. */
    public String argument(String input, String command) {
        if (input.length() == command.length()) {
            return "";
        }
        return input.substring(command.length() + 1).trim();
    }

    /** Splits a deadline argument into description and due date. */
    public String[] deadlineParts(String command) throws OreoException {
        int marker = command.indexOf(" /by ");
        if (marker <= 0 || marker + 5 >= command.length()) {
            throw new OreoException("Use: deadline DESCRIPTION /by DATE");
        }
        String description = command.substring(0, marker).trim();
        String by = command.substring(marker + 5).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new OreoException("Use: deadline DESCRIPTION /by DATE");
        }
        return new String[] {description, by};
    }

    /** Splits an event argument into description, start, and end values. */
    public String[] eventParts(String command) throws OreoException {
        int fromMarker = command.indexOf(" /from ");
        int toMarker = command.indexOf(" /to ");
        if (fromMarker <= 0 || toMarker <= fromMarker + 7 || toMarker + 5 >= command.length()) {
            throw new OreoException("Use: event DESCRIPTION /from START /to END");
        }
        String description = command.substring(0, fromMarker).trim();
        String from = command.substring(fromMarker + 7, toMarker).trim();
        String to = command.substring(toMarker + 5).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new OreoException("Use: event DESCRIPTION /from START /to END");
        }
        return new String[] {description, from, to};
    }
}
