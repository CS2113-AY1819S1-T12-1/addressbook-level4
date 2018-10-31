package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import seedu.address.model.event.DateTime;

/**
 * This class is utility class for method related to @DateTime field
 * and system clock
 */
public class DateTimeUtil {
    public static final DateFormat PAGE_DATE_FORMAT = new SimpleDateFormat("EEEEE dd-MMMMM-yyyy 'at' HH:mm a");

    /**
     * Utility method to get system current timestamp
     * @return current Date
     */
    public static Date getCurrentDateTime() {
        return new Date();
    }

    /**
     * Compare to know how many TimeUnit until or past the date of comparision
     * TimeUnit: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
     */
    public static long daysDiff(DateTime eventDate, Date currentDate, TimeUnit timeUnit) {
        requireAllNonNull(eventDate, currentDate, timeUnit);
        return timeUnit.convert(
                eventDate.dateTime.getTime() - currentDate.getTime(), timeUnit);
    }
}
