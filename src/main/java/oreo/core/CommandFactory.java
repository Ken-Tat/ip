package oreo.core;

import oreo.command.Command;
import oreo.command.DeadlineCommand;
import oreo.command.DeleteCommand;
import oreo.command.EmptyCommand;
import oreo.command.EventCommand;
import oreo.command.ExitCommand;
import oreo.command.ListCommand;
import oreo.command.MarkCommand;
import oreo.command.OnDateCommand;
import oreo.command.TodoCommand;
import oreo.command.UnknownCommand;
import oreo.command.UnmarkCommand;
/** Creates executable commands from parsed command types and input. */
public class CommandFactory {
    /** Builds the command represented by the supplied input and command type. */
    public Command create(CommandType type, String input, Parser parser) throws OreoException {
        return switch (type) {
        case BYE -> new ExitCommand(parser);
        case LIST -> new ListCommand(parser);
        case FIND -> new FindCommand(parser.argument(input, "find"), parser);
        case MARK -> new MarkCommand(parser.argument(input, "mark"), parser);
        case UNMARK -> new UnmarkCommand(parser.argument(input, "unmark"), parser);
        case DELETE -> new DeleteCommand(parser.argument(input, "delete"), parser);
        case DEADLINE -> new DeadlineCommand(parser.argument(input, "deadline"), parser);
        case EVENT -> new EventCommand(parser.argument(input, "event"), parser);
        case TODO -> new TodoCommand(parser.argument(input, "todo"), parser);
        case ON_DATE -> new OnDateCommand(parser.argument(input, "on"), parser);
        case EMPTY -> new EmptyCommand(parser);
        case UNKNOWN -> new UnknownCommand(parser);
        };
    }
}
