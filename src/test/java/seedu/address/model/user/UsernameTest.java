package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid usernames
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("*")); // only non-alphanumeric
        assertFalse(Username.isValidUsername("_j_")); // contains non-alphanumeric

        // valid usernames
        assertTrue(Username.isValidUsername("James Yaputra")); // with upper-case
        assertTrue(Username.isValidUsername("james yaputra")); // lower-case only
        assertTrue(Username.isValidUsername("J")); // one character
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("Richard the 2nd")); // letters and numbers

    }
}
