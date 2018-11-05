package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EMPTY_COMMENT;
import static seedu.address.commons.core.Messages.MESSAGE_LINE_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_REPLY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReplyCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ReplyCommentCommandParserTest {

    private ReplyCommentCommandParser parser = new ReplyCommentCommandParser();
    private String invalidMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE);
    private String emptyMessage = String.format(MESSAGE_INVALID_EMPTY_COMMENT, ReplyCommentCommand.MESSAGE);

    @Test
    void parse_correctFields_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + COMMENT_DESC_REPLY;
        parser.parse(userInput);
        ReplyCommentCommand expectedCommand = new ReplyCommentCommand(targetIndex, VALID_LINE, VALID_COMMENT);
        assertEquals(parser.getIndex(), expectedCommand.getIndex());
        assertEquals(parser.getLine(), expectedCommand.getLine());
        assertEquals(parser.getComment(), expectedCommand.getComment());
    }

    @Test
    void parse_missingFields_failure() {
        //No index, no comment specified
        assertParseFailure(parser, "L/3", invalidMessage);
        //No line, no comment specified
        assertParseFailure(parser, "1", invalidMessage);
        //No index, no line, no comment specified
        assertParseFailure(parser, "", invalidMessage);
        //No index specified
        assertParseFailure(parser, "C/Hi L/1", invalidMessage);
        //No comment specified
        assertParseFailure(parser, "1 L/1", invalidMessage);
        //No line specified
        assertParseFailure(parser, "1 C/Hi", invalidMessage);
        //Empty Comment
        assertParseFailure(parser, "1 C/ L/1", emptyMessage);
        //Index present, line not integer
        assertParseFailure(parser, "1 C/Hi L/Hi", MESSAGE_LINE_INVALID);
    }
}
