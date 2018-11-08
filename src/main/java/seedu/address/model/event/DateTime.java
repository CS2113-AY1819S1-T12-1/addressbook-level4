package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an Event's DateTime field in the event manager.
 */
public class DateTime {
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date should have all value and should be in the format\n"
                    + "dd/MM/yyyy HH:mm with each is a number within\n"
                    + "Month: 1 - 12\n"
                    + "Date: 1 - 31\n"
                    + "Hour: 0-23\n"
                    + "Minute: 0-59\n";
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public final Date dateTime;

    //Create DateTime from the input
    public DateTime(String dateTimeAsString) {
        dateFormat.setLenient(false);
        Date dateTime1 = new Date();
        //Check error
        requireNonNull(dateTimeAsString);

        try {
            dateTime1 = dateFormat.parse(dateTimeAsString);
        } catch (ParseException e) {
            checkArgument(false, MESSAGE_DATETIME_CONSTRAINTS);
        }
        dateTime = dateTime1;
    }

    /**
     * Check if string has correct datetime format
     * @param dateTimeAsString
     * @return boolean
     */
    public static boolean isValidDateTime (String dateTimeAsString) {
        if (dateTimeAsString == null) {
            throw new NullPointerException();
        }

        try {
            dateFormat.setLenient(false);
            dateFormat.parse(dateTimeAsString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return dateFormat.format(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
