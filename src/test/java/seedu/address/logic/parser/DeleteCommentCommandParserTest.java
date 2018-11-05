package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_LINE_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class DeleteCommentCommandParserTest {

    private DeleteCommentCommandParser parser = new DeleteCommentCommandParser();
    private String invalidMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommentCommand.MESSAGE);

    @Test
    void parse_correctFields_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + COMMENT_DESC_DELETE;
        parser.parse(userInput);
        DeleteCommentCommand expectedCommand = new DeleteCommentCommand(targetIndex, VALID_LINE);
        assertEquals(parser.getIndex(), expectedCommand.getIndex());
        assertEquals(parser.getLine(), expectedCommand.getLine());
    }

    @Test
    void parse_missingFields_failure() {
        //No index specified
        assertParseFailure(parser, "L/3", invalidMessage);
        //No line specified
        assertParseFailure(parser, "1", invalidMessage);
        //No index and no comment specified
        assertParseFailure(parser, "", invalidMessage);
        //Index present, line not integer
        assertParseFailure(parser, "1 L/Hi", MESSAGE_LINE_INVALID);
    }
}
