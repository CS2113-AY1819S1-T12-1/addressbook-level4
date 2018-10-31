package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String contact;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String comment;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAttendee> attending = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String name, String contact, String phone, String email, String venue, String datetime,
                           String status, String comment, List<XmlAdaptedTag> tagged,
                           List<XmlAdaptedAttendee> attending) {

        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.venue = venue;
        this.dateTime = datetime;
        this.status = status;
        this.comment = comment;

        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (attending != null) {
            this.attending = new ArrayList<>(attending);
        }
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        name = source.getName().fullName;
        contact = source.getContact().fullContactName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        venue = source.getVenue().value;
        dateTime = source.getDateTime().toString();
        status = source.getStatus().currentStatus;
        comment = source.getComment().value;

        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        attending = source.getAttendance().stream()
                .map(XmlAdaptedAttendee::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> eventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }

        final List<Attendee> eventAttendees = new ArrayList<>();
        for (XmlAdaptedAttendee attendee : attending) {
            eventAttendees.add(attendee.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (contact == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Contact.class.getSimpleName()));
        }
        if (!Contact.isValidContact(contact)) {
            throw new IllegalValueException(Contact.MESSAGE_CONTACT_CONSTRAINTS);
        }
        final Contact modelContact = new Contact(contact);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(venue)) {
            throw new IllegalValueException(Venue.MESSAGE_VENUE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(venue);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_STATUS_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        if (comment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Comment.class.getSimpleName()));
        }
        if (!Comment.isValidComment(comment)) {
            throw new IllegalValueException(Comment.MESSAGE_COMMENT_CONSTRAINTS);
        }
        final Comment modelComment = new Comment(comment);
        final Set<Tag> modelTags = new HashSet<>(eventTags);
        final Set<Attendee> modelAttendees = new HashSet<>(eventAttendees);
        return new Event(modelName, modelContact, modelPhone, modelEmail, modelVenue, modelDateTime, modelStatus,
                modelComment, modelTags, modelAttendees);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(name, otherEvent.name)
                && Objects.equals(contact, otherEvent.contact)
                && Objects.equals(phone, otherEvent.phone)
                && Objects.equals(email, otherEvent.email)
                && Objects.equals(venue, otherEvent.venue)
                && Objects.equals(dateTime, otherEvent.dateTime)
                && Objects.equals(status, otherEvent.status)
                && Objects.equals(comment, otherEvent.comment)
                && tagged.equals(otherEvent.tagged)
                && attending.equals(otherEvent.attending);
    }
}
