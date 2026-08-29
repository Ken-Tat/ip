/** Converts raw user input into the command classification used by Oreo. */
public class Parser {
    /** Identifies the command represented by the supplied input. */
    public CommandType parse(String input) throws OreoException {
        return CommandType.fromInput(input);
    }
}
