//@@author Geraldcdx
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CommentTest {

    @org.junit.Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @org.junit.Test
    public void constructor_invalidComment_throwsIllegalArgumentException() {
        String invalidComment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Comment(invalidComment));
    }

    @Test
    public void isValidComment() {
        // null comment
        Assert.assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // invalid comment
        assertFalse(Comment.isValidComment("")); // empty string
        assertFalse(Comment.isValidComment(" ")); // spaces only

        // valid comment
        assertTrue(Comment.isValidComment("{span}Comments Section{/span}{ol}{/ol}")); // alphabets only
        assertTrue(Comment.isValidComment("{span}Comments Section{/span}{ol}{li}Hello{/li}{/ol}")); // numbers only
    }
}
