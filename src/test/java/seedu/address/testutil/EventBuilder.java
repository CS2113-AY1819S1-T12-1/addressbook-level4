package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Halloween Party";
    public static final String DEFAULT_CONTACT = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATETIME = "31/12/2018 12:00";
    public static final String DEFAULT_STATUS = "NULL";
    public static final String DEFAULT_COMMENT = "{span}Comment Section{/span}{ol}{/ol}";

    private Name name;
    private Contact contact;
    private Phone phone;
    private Email email;
    private Venue venue;
    private DateTime datetime;
    private Status status;
    private Comment comment;
    private Set<Tag> tags;
    private Set<Attendee> attendees;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        contact = new Contact(DEFAULT_CONTACT);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        venue = new Venue(DEFAULT_VENUE);
        datetime = new DateTime(DEFAULT_DATETIME);
        status = new Status(DEFAULT_STATUS);
        comment = new Comment(DEFAULT_COMMENT);
        tags = new HashSet<>();
        attendees = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        contact = eventToCopy.getContact();
        phone = eventToCopy.getPhone();
        email = eventToCopy.getEmail();
        venue = eventToCopy.getVenue();
        datetime = eventToCopy.getDateTime();
        status = eventToCopy.getStatus();
        comment = eventToCopy.getComment();

        tags = new HashSet<>(eventToCopy.getTags());
        attendees = new HashSet<>(eventToCopy.getAttendance());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code attendees} into a {@code Set<Attendee>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withAttendees(String ... attendees) {
        this.attendees = SampleDataUtil.getAttendeeSet(attendees);
        return this;
    }

    /**
     * Parses the {@code attendees} and adds it to the {@code Set<Attendee>} for the {@code Event} that we are building.
     */
    public EventBuilder withAddAttendees(String ... attendees) {
        Set<Attendee> newAttendees = this.attendees;
        Arrays.stream(attendees)
                .map(Attendee::new)
                .forEach(newAttendees::add);
        this.attendees = newAttendees;
        return this;
    }

    /**
     * Parses the {@code attendees} and removes it from the {@code Set<Attendee>}
     * for the {@code Event} that we are building.
     */
    public EventBuilder withRemoveAttendees(String ... attendees) {
        Set<Attendee> newAttendees = this.attendees;
        Arrays.stream(attendees)
                .map(Attendee::new)
                .forEach(newAttendees::remove);
        this.attendees = newAttendees;
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Event} that we are building.
     */
    public EventBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Event} that we are building.
     */
    public EventBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Event} that we are building.
     */
    public EventBuilder withContact(String contact) {
        this.contact = new Contact(contact);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withDateTime(String datetime) {
        this.datetime = new DateTime(datetime);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Event} that we are building.
     */
    public EventBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Event} that we are building.
     */
    public EventBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    public Event build() {
        return new Event(name, contact, phone, email, venue, datetime, status, comment, tags, attendees);
    }
}
