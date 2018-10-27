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
        assertFalse(Comment.isValidComment("^")); // only non-alphanumeric characters
        assertFalse(Comment.isValidComment("peter*")); // contains non-alphanumeric characters

        // valid comment
        assertTrue(Comment.isValidComment("peter jack")); // alphabets only
        assertTrue(Comment.isValidComment("12345")); // numbers only
        assertTrue(Comment.isValidComment("peter the 2nd")); // alphanumeric characters
        assertTrue(Comment.isValidComment("Capital Tan")); // with capital letters
        assertTrue(Comment.isValidComment("David Roger Jackson Ray Jr 2nd")); // long comments
    }
}
