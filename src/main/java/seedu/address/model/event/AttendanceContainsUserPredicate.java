package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.model.user.Username;

/**
 * Predicate to find user with given name
 */
public class AttendanceContainsUserPredicate implements Predicate<Event> {
    private final String username;

    public AttendanceContainsUserPredicate(Username userName) {
        this.username = userName.value;
    }

    @Override
    public boolean test (Event event) {
        return event.getAttendance().stream().anyMatch(attendee -> attendee.attendeeName == username);
    }
}
