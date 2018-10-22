package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Contact contact, Phone phone, Email email, Venue venue, DateTime datetime, Set<Tag> tags,
                 Set<Attendee> attendees) {
        requireAllNonNull(name, contact, phone, email, venue, datetime);

        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.venue = venue;
        this.dateTime = datetime;
        this.tags.addAll(tags);
        this.attendees.addAll(attendees);
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

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
    public Set<Attendee> getAttendees() {
        return Collections.unmodifiableSet(attendees);
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
                && otherEvent.getTags().equals(getTags())
                && otherEvent.getAttendees().equals(getAttendees());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, contact, phone, email, venue, dateTime, tags, attendees);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Attendees: ");
        getAttendees().forEach(builder::append);
        return builder.toString();
    }

}
