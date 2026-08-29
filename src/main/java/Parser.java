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
}
