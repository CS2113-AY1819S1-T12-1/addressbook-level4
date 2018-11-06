package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's status in the event manager.
 */
public class Status {
    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status should either be 'UPCOMING', 'ONGOING' or 'COMPLETED' or 'NULL'.";

    public static final String STATUS_VALIDATION_REGEX = "((\bUPCOMING\b)|(\bONGOING\b)|(\bCOMPLETED\b)|(\bNULL\b))";

    public final String currentStatus;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        currentStatus = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return (test.equals("UPCOMING") || test.equals("ONGOING") || test.equals("COMPLETED") || test.equals("NULL"));
    }

    @Override
    public String toString() {
        return currentStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && currentStatus.equals(((Status) other).currentStatus)); // state check
    }

    @Override
    public int hashCode() {
        return currentStatus.hashCode();
    }
}
