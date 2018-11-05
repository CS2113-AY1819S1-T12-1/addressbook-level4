//@@author Geraldcdx
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommentCommand object
 */
public class DeleteCommentCommandParser implements Parser<DeleteCommentCommand> {

    private int line;
    private Index index;

    public int getLine() {
        return this.line;
    }

    public Index getIndex() {
        return this.index;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @param args arguments to work with
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LINE);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommentCommand.MESSAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_LINE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommentCommand.MESSAGE));
        }
        line = ParserUtil.parseLine(argMultimap.getValue(PREFIX_LINE).get());

        return new DeleteCommentCommand(index , line);
    }
}
