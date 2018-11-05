//@@author Geraldcdx
package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReplyCommentCommand;

/**
 * Testing of ReplyComment class, repetition of comment.initComments(comments); allows
 * easier testcase to be written
 */
class ReplyCommentTest {
    private ReplyComment comment = new ReplyComment();
    private String comments =
            "<span>Comment Section</span>"
                    + "<ol>"
                    + "<li>hello</li>"
                    + "</ol>";

    private String testcase =
            "{span}Comment Section{/span}{ol}{li}hello{/li}{li} (REPLY) Gerald : My name is Gerald{/li}{/ol}";
    @Test
    void replyComment_correctInput_success() throws Exception {
        comment.initComments(comments);
        assertEquals(testcase, comment.replyComment("My name is Gerald", 1, "Gerald"));
    }

    @Test
    void replyComment_incorrectInput_failure() throws Exception {
        comment.initComments(comments);
        assertNotEquals(testcase, comment.replyComment("Chu", 1, "yo"));
    }

    @Test
    void replyComment_lineNotPresent_exceptionThrown() throws Exception {
        try {
            comment.replyComment("Chua", 15, "Yo");
        } catch (Exception e) {
            assertEquals("Line is invalid, try again. Example: replyComment 1 L/1 C/Hello",
                    ReplyCommentCommand.MESSAGE_LINE_INVALID);
        }
    }


}
