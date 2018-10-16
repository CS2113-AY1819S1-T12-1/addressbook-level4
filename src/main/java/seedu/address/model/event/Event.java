package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Event in the event manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    //Todo: add contact field
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final DateTime dateTime;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Attendance attendance;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Phone phone, Email email, Address address, Attendance attendance, DateTime datetime, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, datetime, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.attendance = attendance;
        this.tags.addAll(tags);
        this.dateTime = datetime;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public DateTime getDateTime () { return dateTime; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
                && (otherEvent.getPhone().equals(getPhone()) || otherEvent.getEmail().equals(getEmail()));
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
                && otherEvent.getPhone().equals(getPhone())
                && otherEvent.getEmail().equals(getEmail())
                && otherEvent.getAddress().equals(getAddress())
                && otherEvent.getDateTime().equals(getDateTime())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, dateTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Time: ")
                .append(getDateTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
