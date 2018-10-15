package seedu.address.commons.Comparators;

import seedu.address.model.event.Event;

import java.sql.Time;
import java.util.Comparator;

public class TimeComparator implements Comparator<Event> {
    @Override
    public int compare (Event e1,Event e2) {
        Time e1_time = null;//e1.getTime();
        Time e2_time = null;//e2.getTime();
        return e1_time.compareTo(e2_time);
    }
}
