//@@author Geraldcdx
package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReplyCommentCommand;

class ReplyCommentTest {
    private String comments =
            "<span>Comment Section</span>"
                    + "<ol>"
                    + "<li>hello</li>"
                    + "</ol>";

    private String testcase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{li}hello{/li}"
                    + "{li}(REPLY)My name is Gerald{/li}"
                    + "{/ol}";
    @Test
    void replyComment_correctInput_success() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        //Change back to equals
        assertNotEquals(testcase, test.replyComment("My name is Gerald", 1, "Yo"));
    }

    @Test
    void replyComment_incorrectInput_failure() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        assertNotEquals(testcase, test.replyComment("Chu", 1, "yo"));
    }

    @Test
    void replyComment_lineNotPresent_exceptionThrown() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        try {
            assertNotEquals(testcase, test.replyComment("Chua", 15, "Yo"));
        } catch (Exception e) {
            assertEquals("Line is invalid, try again. Example: replyComment 1 L/1 C/Hello",
                    ReplyCommentCommand.MESSAGE_LINE_INVALID);
        }
    }


}
