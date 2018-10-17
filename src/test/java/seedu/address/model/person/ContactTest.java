package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.event.Contact;
import seedu.address.testutil.Assert;

public class ContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Contact(null));
    }

    @Test
    public void constructor_invalidContact_throwsIllegalArgumentException() {
        String invalidContact = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(invalidContact));
    }

    @Test
    public void isValidContact() {
        // null contact
        Assert.assertThrows(NullPointerException.class, () -> Contact.isValidContact(null));

        // invalid contact
        assertFalse(Contact.isValidContact("")); // empty string
        assertFalse(Contact.isValidContact(" ")); // spaces only
        assertFalse(Contact.isValidContact("^")); // only non-alphanumeric characters
        assertFalse(Contact.isValidContact("peter*")); // contains non-alphanumeric characters

        // valid contact
        assertTrue(Contact.isValidContact("peter jack")); // alphabets only
        assertTrue(Contact.isValidContact("12345")); // numbers only
        assertTrue(Contact.isValidContact("peter the 2nd")); // alphanumeric characters
        assertTrue(Contact.isValidContact("Capital Tan")); // with capital letters
        assertTrue(Contact.isValidContact("David Roger Jackson Ray Jr 2nd")); // long contact names
    }
}
