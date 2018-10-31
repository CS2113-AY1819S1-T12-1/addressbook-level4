package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.model.attendee.Attendee;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the event manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    // Identity fields
    private final Name name;
    private final Contact contact;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Venue venue;
    private final DateTime dateTime;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Attendee> attendees = new HashSet<>();
    private final Status status;
    private final Comment comment;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Contact contact, Phone phone, Email email, Venue venue, DateTime datetime, Status status,
                 Comment comment, Set<Tag> tags, Set<Attendee> attendees) {
        requireAllNonNull(name, contact, phone, email, venue, datetime, status);

        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.venue = venue;
        this.dateTime = datetime;
        this.status = status;
        this.tags.addAll(tags);
        this.attendees.addAll(attendees);
        this.comment = comment;
    }

    public Name getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Venue getVenue() {
        return venue;
    }

    public DateTime getDateTime () {
        return dateTime;
    }

    public Status getStatus () {
        return status;
    }

    public Comment getComment () {
        return comment;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns tags formatted as a string to be passed into Event Page HTML as query string parameter
     */
    public String getTagsString() {
        List<String> tagsList = new ArrayList<>();
        for (Tag t: tags) {
            tagsList.add(t.tagName);
        }
        String tagsString = String.join(" ", tagsList);
        return tagsString;
    }

    /**
     * Returns an immutable attendee set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Attendee> getAttendance() {
        return Collections.unmodifiableSet(attendees);
    }

    /**
     * Returns attendee list formatted as a string to be passed into Event Page HTML as query string parameter
     */
    public String getAttendanceString() {
        TreeSet<String> attendeesSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Attendee a: attendees) {
            attendeesSet.add(a.attendeeName);
        }
        String attendeesString = String.join("<br>", attendeesSet);
        return attendeesString;
    }

    /**
     * Returns true if both events of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && (otherEvent.getPhone().equals(getPhone()) || otherEvent.getEmail().equals(getEmail())
                    || otherEvent.getContact().equals(getContact()));
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getContact().equals(getContact())
                && otherEvent.getPhone().equals(getPhone())
                && otherEvent.getEmail().equals(getEmail())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getDateTime().equals(getDateTime())
                && otherEvent.getStatus().equals(getStatus())
                && otherEvent.getTags().equals(getTags())
                && otherEvent.getAttendance().equals(getAttendance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, contact, phone, email, venue, dateTime, status, comment, tags, attendees);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Contact: ")
                .append(getContact())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Time: ")
                .append(getDateTime())
                .append(" Status: ")
                .append(getStatus())
                .append(" Comment: ")
                .append(getComment())
                .append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Attendees: ");
        getAttendance().forEach(builder::append);
        return builder.toString();
    }

}
