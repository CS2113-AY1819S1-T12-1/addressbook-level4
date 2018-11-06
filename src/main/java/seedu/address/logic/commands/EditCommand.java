package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
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
 * Edits the details of an existing event in the event manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_CONTACT + "CONTACT] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_VENUE + "VENUE] "
            + "[" + PREFIX_DATETIME + "DD/MM/YYYY HH:MM "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_ATTENDEE + "ATTENDEE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";


    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event manager.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (!model.getAdminStatus()) {
            throw new CommandException(MESSAGE_ADMIN);
        }

        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.updateEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitEventManager();
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */

    public static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {

        assert eventToEdit != null;

        Name updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        Contact updatedContact = editEventDescriptor.getContact().orElse(eventToEdit.getContact());
        Phone updatedPhone = editEventDescriptor.getPhone().orElse(eventToEdit.getPhone());
        Email updatedEmail = editEventDescriptor.getEmail().orElse(eventToEdit.getEmail());
        Venue updatedVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        DateTime updatedDateTime = editEventDescriptor.getDateTime().orElse(eventToEdit.getDateTime());
        Status eventStatus = new Status(Status.setStatus(updatedDateTime));
        Comment updatedComment = editEventDescriptor.getComment().orElse(eventToEdit.getComment());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());
        Set<Attendee> updatedAttendees = editEventDescriptor.getAttendees().orElse(eventToEdit.getAttendance());

        return new Event(updatedName, updatedContact, updatedPhone, updatedEmail, updatedVenue, updatedDateTime,
                eventStatus, updatedComment, updatedTags, updatedAttendees);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Name name;
        private Contact contact;
        private Phone phone;
        private Email email;
        private Venue venue;
        private DateTime dateTime;
        private Comment comment;

        private Set<Tag> tags;
        private Set<Attendee> attendees;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setContact(toCopy.contact);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setVenue(toCopy.venue);
            setDate(toCopy.dateTime);
            setComment(toCopy.comment);
            setTags(toCopy.tags);
            setAttendees(toCopy.attendees);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, contact, phone, email, venue, dateTime, comment, tags, attendees);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public Optional<Contact> getContact() {
            return Optional.ofNullable(contact);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setDate(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public Optional<Comment> getComment() {
            return Optional.ofNullable(comment);
        }


        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Sets {@code attendees} to this object's {@code attendees}.
         * A defensive copy of {@code attendees} is used internally.
         */
        public void setAttendees(Set<Attendee> attendees) {
            this.attendees = (attendees != null) ? new HashSet<>(attendees) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable attendee set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code attendees} is null.
         */
        public Optional<Set<Attendee>> getAttendees() {
            return (attendees != null) ? Optional.of(Collections.unmodifiableSet(attendees)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getContact().equals(e.getContact())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getVenue().equals(e.getVenue())
                    && getDateTime().equals(e.getDateTime())
                    && getComment().equals(e.getComment())
                    && getTags().equals(e.getTags())
                    && getAttendees().equals(e.getAttendees());
        }
    }
}
