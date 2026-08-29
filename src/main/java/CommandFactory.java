/** Creates executable commands from parsed command types and input. */
public class CommandFactory {
    /** Builds the command represented by the supplied input and command type. */
    public Command create(CommandType type, String input, Parser parser) throws OreoException {
        return switch (type) {
        case BYE -> new ExitCommand();
        case LIST -> new ListCommand();
        case MARK -> new MarkCommand(parser.argument(input, "mark"));
        case UNMARK -> new UnmarkCommand(parser.argument(input, "unmark"));
        case DELETE -> new DeleteCommand(parser.argument(input, "delete"));
        case DEADLINE -> new DeadlineCommand(parser.argument(input, "deadline"));
        case EVENT -> new EventCommand(parser.argument(input, "event"));
        case TODO -> new TodoCommand(parser.argument(input, "todo"));
        case ON_DATE -> new OnDateCommand(parser.argument(input, "on"));
        case EMPTY -> new EmptyCommand();
        case UNKNOWN -> new UnknownCommand();
        };
    }
}
