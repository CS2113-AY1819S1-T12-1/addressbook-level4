package seedu.address.model.event;


import seedu.address.model.user.User;
import java.util.function.Predicate;

public class AttendanceContainsUserPredicate implements Predicate<Event> {
    private final String username;

    public AttendanceContainsUserPredicate(User name) {
        this.username = name.getUsername().value;
    }
    //To be reimplement later when user is modified
    @Override
    public boolean test (Event event) {
        return username.equalsIgnoreCase(event.getAttendance().toString());
    }
}
