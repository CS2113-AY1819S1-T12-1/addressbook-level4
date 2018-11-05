//@@author Geraldcdx
package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

class CommentFacadeTest {

    private CommentFacade stub = new CommentFacade();

    private String comments =
            "<span>Comment Section</span>"
                    + "<ol>"
                    + "<li>hello</li>"
                    + "</ol>";

    private String addTestCase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{li}hello{/li}"
                    + "{li}Yo : hi{/li}"
                    + "{/ol}";

    private String deleteTestCase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{/ol}";

    private String replyTestCase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{li}hello{/li}"
                    + "{li} (REPLY) Yo : hi{/li}"
                    + "{/ol}";

    private String emptyTestCase =
            "{span}Comment Section{/span}"
                    + "{ol}"
                    + "{/ol}";
    @Test
    void addComment_correctInput_success() {
        assertEquals(addTestCase, stub.addComment(comments, "hi", "Yo"));
    }

    @Test
    void addComment_wrongInput_failure() {
        assertNotEquals(emptyTestCase, stub.addComment(comments, "hi", "Yo"));
    }

    @Test
    void deleteComment_correctInput_success() throws CommandException {
        assertEquals(deleteTestCase, stub.deleteComment(comments, 1));
    }

    @Test
    void deleteComment_wrongInput_failure() throws CommandException {
        assertNotEquals(comments, stub.deleteComment(comments, 1));
    }

    @Test
    void replyComment_correctInput_success() throws CommandException {
        assertEquals(replyTestCase, stub.replyComment(comments, "hi", 1, "Yo"));
    }

    @Test
    void replyComment_wrongInput_failure() throws CommandException {
        assertNotEquals(comments, stub.replyComment(comments, "hi", 1, "Yo"));
    }
}
