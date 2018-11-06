package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Date;

/**
 * Represents an Event's status in the event manager.
 */
public class Status {
    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status should either be 'UPCOMING', or 'COMPLETED' or 'NULL'.";

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
        return (test.equals("UPCOMING") || test.equals("COMPLETED") || test.equals("NULL"));
    }

    /**
     * Gets the status of the event based on current date {@code Date()}.
     *
     * @param datetime Datetime of event.
     */
    public static String setStatus(DateTime datetime) {
        requireNonNull(datetime);
        Date currentDate = new Date();
        Date eventDate = datetime.dateTime;
        String newStatus;

        if (eventDate.before(currentDate)) {
            newStatus = "COMPLETED";
        } else if (eventDate.after(currentDate)) {
            newStatus = "UPCOMING";
        } else {
            newStatus = "NULL";
        }

        return newStatus;
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
