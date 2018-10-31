package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;
import seedu.address.model.event.AttendanceContainsUserPredicate;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;

/**
 * This class is utility class for method related to @DateTime field
 * and system clock
 */
public class DateTimeManager {
    public static final DateFormat PAGE_DATE_FORMAT = new SimpleDateFormat("EEEEE dd-MMMMM-yyyy 'at' HH:mm a");

    private static AttendanceContainsUserPredicate predicate;

    //Use to get computer current datetime, assuming computer date is set correctly
    public static Date getCurrentDateTime() {
        return new Date();
    }

    /**Compare to know how many TimeUnit until or past the date of comparision
     *TimeUnit: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
     */
    public static long daysDiff(DateTime eventDate, Date currentDate, TimeUnit timeUnit) {
        requireAllNonNull(eventDate, currentDate, timeUnit);
        return timeUnit.convert(
                eventDate.dateTime.getTime() - currentDate.getTime(), timeUnit);
    }

    //Require the current model to get the list
    public static ObservableList<Event> getAttendingEventList(Model model, User currentUser) {
        requireAllNonNull(model, currentUser);
        currentUser.getUsername();
        model.updateFilteredEventList(predicate);
        return model.getFilteredEventList();
    }

    private static ObservableList<Date> getAttendingEventDateList(Model model, User currentUser) {
        requireAllNonNull(model, currentUser);

        ObservableList<Date> dateList = null;
        for (Event event: getAttendingEventList(model, currentUser)) {
            dateList.add(event.getDateTime().dateTime);
        }

        if (dateList.size() == 0) {
            //announce user that they have not registered for any event
        }

        return dateList;
    }

    private static void writeToICalenderFile(ObservableList<Date> dateList) {

    }

    public static void exportICalenderFile(Model model, User currentUser) {
        writeToICalenderFile(getAttendingEventDateList(model, currentUser));
    }
}
