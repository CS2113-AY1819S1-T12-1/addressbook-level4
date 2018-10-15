package seedu.address.commons.Comparators;

import seedu.address.model.event.Event;
import java.sql.Time;
import java.util.Comparator;
import java.util.Date;

public class DateThenTimeComparator implements Comparator<Event> {
    @Override
    public int compare (Event e1, Event e2) {
        Date e1_date = null;//e1.getDate();
        Date e2_date = null;//e2.getDate();
        Time e1_time = null;//e1.getTime();
        Time e2_time = null;//e2.getTime();
        int dateCompare = e1_date.compareTo(e2_date);
        if (dateCompare > 0) {
            return 1;
        } else if (dateCompare < 0) {
            return -1;
        } else {
            return e1_time.compareTo(e2_time);
        }
    }
}
