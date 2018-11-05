//@@author Geraldcdx

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EMPTY_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommentCommand object
 */
public class AddCommentCommandParser implements Parser<AddCommentCommand> {

    private String comment = null;
    private Index index = null;

    public String getComment() {
        return comment;
    }
    public Index getIndex() {
        return index;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @param args arguments to work wit
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMMENT);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommentCommand.MESSAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommentCommand.MESSAGE));
        }
        comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get());
        if (comment.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_EMPTY_COMMENT, AddCommentCommand.MESSAGE));
        }
        return new AddCommentCommand(index, comment);
    }
}
