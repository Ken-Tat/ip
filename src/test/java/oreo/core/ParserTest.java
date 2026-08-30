package oreo.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Tests the parser's validation and extraction of command arguments. */
class ParserTest {
    private final Parser parser = new Parser(new CommandFactory());

    @Test
    void deadlineParts_validCommand_returnsDescriptionAndDate() throws OreoException {
        assertArrayEquals(
                new String[] {"return book", "2019-10-15"},
                parser.deadlineParts("return book /by 2019-10-15"));
    }

    @Test
    void deadlineParts_missingOrEmptyPart_throwsUsageError() {
        assertUsageError(() -> parser.deadlineParts("return book"),
                "Use: deadline DESCRIPTION /by DATE");
        assertUsageError(() -> parser.deadlineParts(" /by 2019-10-15"),
                "Use: deadline DESCRIPTION /by DATE");
        assertUsageError(() -> parser.deadlineParts("return book /by "),
                "Use: deadline DESCRIPTION /by DATE");
    }

    @Test
    void eventParts_validCommand_returnsDescriptionAndDates() throws OreoException {
        assertArrayEquals(
                new String[] {"project meeting", "2019-10-15", "2019-10-16"},
                parser.eventParts("project meeting /from 2019-10-15 /to 2019-10-16"));
    }

    @Test
    void eventParts_missingOrReversedMarkers_throwsUsageError() {
        assertUsageError(() -> parser.eventParts("project meeting /from 2019-10-15"),
                "Use: event DESCRIPTION /from START /to END");
        assertUsageError(() -> parser.eventParts("project meeting /to 2019-10-16 /from 2019-10-15"),
                "Use: event DESCRIPTION /from START /to END");
        assertUsageError(() -> parser.eventParts("project meeting /from  /to 2019-10-16"),
                "Use: event DESCRIPTION /from START /to END");
    }

    @Test
    void taskIndex_validOneBasedNumber_returnsZeroBasedIndex() throws OreoException {
        assertEquals(0, parser.taskIndex("1", 3));
        assertEquals(2, parser.taskIndex("3", 3));
    }

    @Test
    void taskIndex_emptyNonNumericOrOutOfRange_throwsSpecificError() {
        assertUsageError(() -> parser.taskIndex("", 3), "Sooo which task is it?");
        assertUsageError(() -> parser.taskIndex("abc", 3), "That is not a valid task number.");
        assertUsageError(() -> parser.taskIndex("0", 3), "I can't find that task number.");
        assertUsageError(() -> parser.taskIndex("4", 3), "I can't find that task number.");
        assertUsageError(() -> parser.taskIndex("1", 0), "I can't find that task number.");
    }

    @Test
    void argument_commandWithoutArgument_returnsEmptyString() {
        assertEquals("", parser.argument("list", "list"));
        assertEquals("buy milk", parser.argument("todo buy milk", "todo"));
    }

    @Test
    void findCommand_isRecognised() {
        assertEquals(CommandType.FIND, CommandType.fromInput("find book"));
        assertEquals(CommandType.FIND, CommandType.fromInput("find"));
    }

    private void assertUsageError(ThrowingOperation operation, String message) {
        OreoException exception = assertThrows(OreoException.class, operation::run);
        assertEquals(message, exception.getMessage());
    }

    @FunctionalInterface
    private interface ThrowingOperation {
        void run() throws OreoException;
    }
}
