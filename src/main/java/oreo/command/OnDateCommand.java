package oreo.command;

import oreo.core.*;
import java.time.LocalDate;

/** Command that displays deadlines and events occurring on a date. */
public class OnDateCommand extends Command {
    private final String dateText;

    /** Creates a date-query command. */
    public OnDateCommand(String dateText, Parser parser) {
        super(parser);
        this.dateText = dateText;
    }

    @Override
    public void execute(AppContext context) throws OreoException {
        if (dateText.isEmpty()) {
            throw new OreoException("Use: on YYYY-MM-DD");
        }
        final LocalDate date;
        try {
            date = DateTimeParser.parseDate(dateText);
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
        context.ui.showTasksOnDate(context.tasks, date);
    }
}
