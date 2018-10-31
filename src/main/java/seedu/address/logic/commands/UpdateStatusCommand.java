package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Comment;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Status;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * Updates status of events listed in {@code lastShownList}.
 */
public class UpdateStatusCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_SUCCESS = "Update success";

    public static final String MESSAGE_MISSING_EVENTS = "No events to update";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (lastShownList.isEmpty()) {
            return new CommandResult(MESSAGE_MISSING_EVENTS);
        }

        for (Event event : lastShownList) {
            Event updatedEvent = createUpdatedStatusEvent(event);

            model.updateEvent(event, updatedEvent);
            model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Stores the details to edit the event with.
     * Every field is retained as per the original event except for {@code updatedStatus} which is updated.
     */
    public static Event createUpdatedStatusEvent(Event eventToUpdate) {

        assert eventToUpdate != null;

        Name updatedName = eventToUpdate.getName();
        Contact updatedContact = eventToUpdate.getContact();
        Phone updatedPhone = eventToUpdate.getPhone();
        Email updatedEmail = eventToUpdate.getEmail();
        Venue updatedVenue = eventToUpdate.getVenue();
        DateTime updatedDateTime = eventToUpdate.getDateTime();
        Status updatedStatus = new Status(updatedDateTime);
        Comment updatedComment = eventToUpdate.getComment();
        Set<Tag> updatedTags = eventToUpdate.getTags();
        Set<Attendee> updatedAttendees = eventToUpdate.getAttendees();

        return new Event(updatedName, updatedContact, updatedPhone, updatedEmail, updatedVenue, updatedDateTime,
                updatedStatus, updatedComment, updatedTags, updatedAttendees);
    }
}

