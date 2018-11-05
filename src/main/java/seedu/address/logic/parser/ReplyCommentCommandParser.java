//@@author Geraldcdx
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EMPTY_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReplyCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReplyCommentCommand object
 */
public class ReplyCommentCommandParser implements Parser<ReplyCommentCommand> {

    private int line;
    private String comment;
    private Index index;

    public int getLine()
    {
        return this.line;
    }

    public String getComment() {
        return this.comment;
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
    public ReplyCommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LINE, PREFIX_COMMENT, PREFIX_NAME);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_LINE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE));
        }
        line = ParserUtil.parseLine(argMultimap.getValue(PREFIX_LINE).get());
        if (!argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE));
        }
        comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get());
        if (comment.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_EMPTY_COMMENT, ReplyCommentCommand.MESSAGE));
        }

        return new ReplyCommentCommand(index , line , comment);
    }
}
