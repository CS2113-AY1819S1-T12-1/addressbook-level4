package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final Boolean DEFAULT_ATTENDANCE = false;
    public static final String DEFAULT_DATETIME = "31/12/2018 12:00";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Attendance attendance;
    private Set<Tag> tags;
    private DateTime datetime;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        attendance = new Attendance(DEFAULT_ATTENDANCE);
        datetime = new DateTime(DEFAULT_DATETIME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        phone = eventToCopy.getPhone();
        email = eventToCopy.getEmail();
        address = eventToCopy.getAddress();
        attendance = eventToCopy.getAttendance();
        datetime = eventToCopy.getDateTime();
        tags = new HashSet<>(eventToCopy.getTags());
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
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        this.address = new Address(address);
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
     * Sets the {@code Attendance} of the {@code Event} that we are building.
     */
    public EventBuilder withAttendance(Boolean attendance) {
        this.attendance = new Attendance(attendance);
        return this;
    }

    public EventBuilder withDateTime(String datetimeAsString) {
        this.datetime = new DateTime(datetimeAsString);
        return this;
    }

    public Event build() {
        return new Event(name, phone, email, address, attendance, datetime, tags);
    }

}
