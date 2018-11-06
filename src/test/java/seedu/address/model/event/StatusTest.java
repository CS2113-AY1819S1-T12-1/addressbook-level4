package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status((String) null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        //Assert.assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid Status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("^")); // only non-alphanumeric characters
        assertFalse(Status.isValidStatus("peter*")); // contains non-alphanumeric characters
        assertFalse(Status.isValidStatus("TOMORROW")); // not accepted status

        // valid status
        assertTrue(Status.isValidStatus("UPCOMING")); // UPCOMING
        assertTrue(Status.isValidStatus("COMPLETED")); // COMPLETED
        assertTrue(Status.isValidStatus("NULL")); // NULL
    }
}
