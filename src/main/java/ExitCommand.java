/** Command that displays the goodbye message and ends the application. */
public class ExitCommand extends Command {
    public ExitCommand(Parser parser) { super(parser); }

    @Override
    public void execute(AppContext context) {
        context.ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
