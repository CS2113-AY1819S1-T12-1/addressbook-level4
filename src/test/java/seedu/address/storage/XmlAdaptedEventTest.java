package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_ATTENDEE = "AL@K@Z@M";
    private static final String INVALID_NAME = "D@NC^N& ^N TH3 D@RK";
    private static final String INVALID_CONTACT = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_VENUE = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATETIME = "31-2-2018 10:10";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_CONTACT = BENSON.getContact().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_VENUE = BENSON.getVenue().toString();
    private static final String VALID_DATETIME = BENSON.getDateTime().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final String VALID_COMMENT = "{span}Comment Section{/span}{ol}{/ol}"; //TODO for Comment
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedAttendee> VALID_ATTENDEES = BENSON.getAttendance().stream()
            .map(XmlAdaptedAttendee::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_valideventDetails_returnsevent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(BENSON);
        assertEquals(BENSON, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(INVALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidContact_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, INVALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Contact.MESSAGE_CONTACT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullContact_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Contact.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, INVALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, null, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, INVALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, null,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                INVALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Venue.MESSAGE_VENUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                null, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_VENUE,
                INVALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_VENUE,
                null, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, invalidTags, VALID_ATTENDEES);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidAttendees_throwsIllegalValueException() {
        List<XmlAdaptedAttendee> invalidAttendees = new ArrayList<>(VALID_ATTENDEES);
        invalidAttendees.add(new XmlAdaptedAttendee(INVALID_ATTENDEE));
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, invalidAttendees);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }
}
