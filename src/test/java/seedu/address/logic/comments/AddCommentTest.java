//@@author Geraldcdx
package seedu.address.logic.comments;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/*
 * Testing the AddComment class. There is a need to comment.initComments(comments); in every function because
 * it is easier to write simple testcases
 */
class AddCommentTest {
    private static AddComment comment = new AddComment();
    private static String comments =
            "<span>Comment Section</span>"
                    + "<ol>"
                    + "<li>hello</li>"
                    + "</ol>";

    private String testCase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{li}hello{/li}"
                    + "{li}Yo : hi{/li}"
                    + "{/ol}";

    /**
     * There is a need to reintialise all the  comments again
     */
    @Test
    void addComment_correctInput_success() {
        comment.initComments(comments);
        assertEquals(testCase, comment.addComment("hi", "Yo"));
    }
    @Test
    void addComment_incorrectInput_failure() {
        comment.initComments(comments);
        assertNotEquals(testCase, comment.addComment("i", "yo"));
    }

}
