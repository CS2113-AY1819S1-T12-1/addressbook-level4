//@@author Geraldcdx
package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Vector;

import org.junit.jupiter.api.Test;

/**
 * A test class to test all methods in the Comments class
 */
class CommentsTest {

    /**
     *  Since Comments is an abstract class it cannot be instantiated but we can use
     */
    private AddComment stub = new AddComment();
    private String stubStr = "{span}Comment Section{/span}{ol}{li}Hi{/li}{/ol}";
    private String stubStrOutput = "<span>Comment Section</span><ol><li>Hi</li></ol>";
    private Vector stubVector = new Vector();

    @Test
    void replaceBrackets_correctInput_success() {
        assertEquals(stubStrOutput, stub.replaceBrackets(stubStr));
    }
    @Test
    void replaceBrackets_wrongInput_failure() {

        assertNotEquals(stubStrOutput + "{", stub.replaceBrackets(stubStr));
    }

    @Test
    void parseCommentSection_correctInput_success() {
        stubVector.add("Hi");
        assertEquals(stubVector, stub.parseCommentSection(stubStrOutput));
    }
    @Test
    void parseCommentSection_wrongInput_failure() {
        assertNotEquals(stubVector + ">", stub.parseCommentSection(stubStrOutput));
    }

    @Test
    void rewrite_correctInput_success() {
        stubVector.add("Hi");
        assertEquals(stubStr, stub.rewrite(stubVector));
    }

    @Test
    void rewrite_wrongInput_fail() {
        assertNotEquals(stubStr + "str", stub.rewrite(stubVector));
    }
}
