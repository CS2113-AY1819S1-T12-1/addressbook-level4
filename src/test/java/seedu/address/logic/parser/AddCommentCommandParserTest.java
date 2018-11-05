package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EMPTY_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 *
 */
class AddCommentCommandParserTest {

    private AddCommentCommandParser parser = new AddCommentCommandParser();
    private String invalidMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommentCommand.MESSAGE);
    private String emptyCommentMessage = String.format(MESSAGE_INVALID_EMPTY_COMMENT, AddCommentCommand.MESSAGE);

    @Test
    void parse_correctFields_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + COMMENT_DESC_ADD;
        parser.parse(userInput);
        AddCommentCommand expectedCommand = new AddCommentCommand(targetIndex, VALID_COMMENT);
        assertEquals(parser.getIndex(), expectedCommand.getIndex());
        assertEquals(parser.getComment(), expectedCommand.getComment());
    }

    @Test
    void parse_missingFields() {
        //No index specified
        assertParseFailure(parser, "C/Hi", invalidMessage);
        //No comment specified
        assertParseFailure(parser, "1", invalidMessage);
        //No index and no comment specified
        assertParseFailure(parser, "", invalidMessage);
        //Index present, empty String
        assertParseFailure(parser, "1 C/", emptyCommentMessage);
    }
}
