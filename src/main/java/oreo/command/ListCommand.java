package oreo.command;

import oreo.core.AppContext;
import oreo.core.Parser;
/** Command that displays the current task list. */
public class ListCommand extends Command {
    public ListCommand(Parser parser) {
        super(parser);
    }

    @Override
    public void execute(AppContext context) {
        context.ui.showTaskList(context.tasks);
    }
}
