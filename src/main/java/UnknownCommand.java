/** Command representing input that is not recognized. */
public class UnknownCommand extends Command {
    public UnknownCommand(Parser parser) { super(parser); }

    @Override
    public void execute(AppContext context) throws OreoException {
        throw new OreoException("I cannot comprehend your English.");
    }
}
