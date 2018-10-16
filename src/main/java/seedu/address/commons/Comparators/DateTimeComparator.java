package seedu.address.commons.Comparators;

import seedu.address.model.event.Event;
import java.sql.Time;
import java.util.Comparator;
import java.util.Date;

public class DateTimeComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getDateTime().dateTime.compareTo(e2.getDateTime().dateTime);
    }
}
