package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDEE_DESC_HAN;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDEE_DESC_TED;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ATTENDEE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_HAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_TED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.AMY;
import static seedu.address.testutil.TypicalEvents.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(BOB).withTags(VALID_TAG_FRIEND).withAttendees(VALID_ATTENDEE_TED)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + ATTENDEE_DESC_TED,
                new AddCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + ATTENDEE_DESC_TED,
                new AddCommand(expectedEvent));

        // multiple contacts - last contact accepted
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_AMY + CONTACT_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + ATTENDEE_DESC_TED,
                new AddCommand(expectedEvent));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + ATTENDEE_DESC_TED,
                new AddCommand(expectedEvent));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + ATTENDEE_DESC_TED,
                new AddCommand(expectedEvent));

        // multiple venues - last venue accepted
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_AMY + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + ATTENDEE_DESC_TED,
                new AddCommand(expectedEvent));

        // multiple datetime - last datetime accepted
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_AMY + VENUE_DESC_BOB + DATETIME_DESC_AMY + DATETIME_DESC_BOB + TAG_DESC_FRIEND
                + ATTENDEE_DESC_TED, new AddCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDEE_DESC_TED
                + ATTENDEE_DESC_HAN, new AddCommand(expectedEventMultipleTags));

        // multiple attendees - all accepted
        Event expectedEventMultipleAttendees = new EventBuilder(BOB).withAttendees(VALID_ATTENDEE_TED,
                VALID_ATTENDEE_HAN).build();
        assertParseSuccess(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + ATTENDEE_DESC_TED
                + ATTENDEE_DESC_HAN, new AddCommand(expectedEventMultipleAttendees));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder(AMY).withTags().withAttendees().build();
        assertParseSuccess(parser, NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + DATETIME_DESC_AMY, new AddCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_NAME_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB, expectedMessage);

        // missing contact prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_CONTACT_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_VENUE_BOB + DATETIME_DESC_BOB, expectedMessage);

        // missing datetime prefix
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_VENUE_BOB + VALID_DATETIME_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_CONTACT_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_VENUE_BOB + VALID_DATETIME_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid contact
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_CONTACT_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Contact.MESSAGE_CONTACT_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_VENUE_DESC + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Venue.MESSAGE_VENUE_CONSTRAINTS);

        // invalid dateTime
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + INVALID_DATETIME_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid attendee
        assertParseFailure(parser, NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VENUE_DESC_BOB + DATETIME_DESC_BOB + VALID_TAG_FRIEND + VALID_TAG_HUSBAND + INVALID_ATTENDEE_DESC,
                Attendee.MESSAGE_ATTENDEE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + CONTACT_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_VENUE_DESC + DATETIME_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + CONTACT_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VENUE_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
