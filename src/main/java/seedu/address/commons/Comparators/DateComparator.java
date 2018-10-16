package seedu.address.commons.Comparators;

import seedu.address.model.event.Event;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        Date e1_date = null;//e1.getDate();
        Date e2_date = null;// e2.getDate();
        return e1_date.compareTo(e2_date);
    }
}

