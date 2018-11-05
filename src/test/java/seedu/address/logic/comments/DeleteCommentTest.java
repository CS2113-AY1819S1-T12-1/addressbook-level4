//@@author Geraldcdx
package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommentCommand;

/**
 * To test DeleteComment class, there is a need to comment.initComments(comments); because it is easier
 * to write simple testcases
 */
class DeleteCommentTest {

    private DeleteComment comment = new DeleteComment();
    private String comments =
            "<span>Comment Section</span>"
                    + "<ol>"
                    + "<li>hello</li>"
                    + "<li>My name is Gerald</li>"
                    + "</ol>";

    private String testcase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{li}hello{/li}"
                    + "{/ol}";
    @Test
    void deleteComment_correctInput_success() throws Exception {
        comment.initComments(comments);
        assertEquals(testcase, comment.deleteComment(2));
    }

    @Test
    void deleteComment_incorrectInput_failure() throws Exception {
        comment.initComments(comments);
        assertNotEquals(testcase, comment.deleteComment(1));
    }

    @Test
    void deleteComment_lineNotPresent_exceptionThrown() throws Exception {
        try {
            comment.initComments(comments);
            comment.deleteComment(15);
        } catch (Exception e) {
            assertEquals("Line is invalid, try again. Example: deleteComment 1 L/2",
                    DeleteCommentCommand.MESSAGE_LINE_INVALID);
        }
    }
}
