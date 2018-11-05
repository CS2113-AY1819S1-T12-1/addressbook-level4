package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.EventManager;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.testutil.EditEventDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final int VALID_LINE = 1;
    public static final String VALID_ADMIN_USERNAME = "admin";
    public static final String VALID_ADMIN_PASSWORD = "root";
    public static final String VALID_USERNAME = "Johnny Bravo";
    public static final String VALID_PASSWORD = "pass@12345";
    public static final String VALID_ATTENDEE_TED = "Ted Bacan";
    public static final String VALID_ATTENDEE_HAN = "Han Christian";
    public static final String VALID_NAME_AMY = "Night Cycling";
    public static final String VALID_NAME_BOB = "House Movie Screening";
    public static final String VALID_CONTACT_AMY = "Amy Bee";
    public static final String VALID_CONTACT_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_VENUE_AMY = "Block 312, Amy Street 1";
    public static final String VALID_VENUE_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DATETIME_AMY = "17/8/2018 21:15";
    public static final String VALID_DATETIME_BOB = "7/7/2017 7:07";
    public static final String VALID_STATUS_AMY = "NULL";
    public static final String VALID_STATUS_BOB = "NULL";
    public static final String VALID_COMMENT_AMY = "{span}Comment Section{/span}{ol}{/ol}";
    public static final String VALID_COMMENT_BOB = "{span}Comment Section{/span}{ol}{/ol}";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_COMMENT = "Hi";

    public static final String ADMIN_USERNAME_DESC = " " + PREFIX_USERNAME + VALID_ADMIN_USERNAME;
    public static final String ADMIN_PASSWORD_DESC = " " + PREFIX_PASSWORD + VALID_ADMIN_PASSWORD;
    public static final String USERNAME_DESC = " " + PREFIX_USERNAME + VALID_USERNAME;
    public static final String PASSWORD_DESC = " " + PREFIX_PASSWORD + VALID_PASSWORD;
    public static final String ATTENDEE_DESC_TED = " " + PREFIX_ATTENDEE + VALID_ATTENDEE_TED;
    public static final String ATTENDEE_DESC_HAN = " " + PREFIX_ATTENDEE + VALID_ATTENDEE_HAN;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String CONTACT_DESC_AMY = " " + PREFIX_CONTACT + VALID_CONTACT_AMY;
    public static final String CONTACT_DESC_BOB = " " + PREFIX_CONTACT + VALID_CONTACT_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String VENUE_DESC_AMY = " " + PREFIX_VENUE + VALID_VENUE_AMY;
    public static final String VENUE_DESC_BOB = " " + PREFIX_VENUE + VALID_VENUE_BOB;
    public static final String DATETIME_DESC_AMY = " " + PREFIX_DATETIME + VALID_DATETIME_AMY;
    public static final String DATETIME_DESC_BOB = " " + PREFIX_DATETIME + VALID_DATETIME_BOB;
    public static final String COMMENT_DESC_ADD = " " + PREFIX_COMMENT + VALID_COMMENT;
    public static final String COMMENT_DESC_DELETE = " " + PREFIX_LINE + VALID_LINE;
    public static final String COMMENT_DESC_REPLY = " " + PREFIX_COMMENT + VALID_COMMENT + " "
            + PREFIX_LINE + VALID_LINE;
    public static final String COMMENT_DESC_AMY = " " + PREFIX_COMMENT + VALID_COMMENT_AMY;
    public static final String COMMENT_DESC_BOB = " " + PREFIX_COMMENT + VALID_COMMENT_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USERNAME + "j4!"; // '!' not allowed
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "pass word"; // space not allowed
    public static final String INVALID_ATTENDEE_DESC = " " + PREFIX_ATTENDEE + "R@chel"; // '@' not allowed in attendees
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_CONTACT_DESC = " " + PREFIX_CONTACT + "James&"; // '&' not allowed in contact
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE; // empty string not allowed for venues
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + "31-12-2018 4.30"; //Incorrect format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEventDescriptor DESC_AMY;
    public static final EditCommand.EditEventDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditEventDescriptorBuilder().withName(VALID_NAME_AMY).withContact(VALID_CONTACT_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withVenue(VALID_VENUE_AMY)
                .withDateTime(VALID_DATETIME_AMY).withComment(VALID_COMMENT_AMY).withTags(VALID_TAG_FRIEND)
                .withAttendees(VALID_ATTENDEE_HAN).build();
        DESC_BOB = new EditEventDescriptorBuilder().withName(VALID_NAME_BOB).withContact(VALID_CONTACT_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withVenue(VALID_VENUE_BOB)
                .withDateTime(VALID_DATETIME_AMY).withComment(VALID_COMMENT_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered event list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        EventManager expectedEventManager = new EventManager(actualModel.getEventManager());
        List<Event> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEventList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedEventManager, actualModel.getEventManager());
            assertEquals(expectedFilteredList, actualModel.getFilteredEventList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s event manager.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());

        final String[] splitName = event.getName().fullName.split("\\s+");
        List<String> nameList = Arrays.asList(splitName[0]);
        Map<Prefix, List<String> >keywordMap = new HashMap<>();
        keywordMap.put(PREFIX_NAME, nameList);
        model.updateFilteredEventList(new EventContainsKeywordsPredicate(keywordMap));
        assertEquals(1, model.getFilteredEventList().size());
    }

    /**
     * Deletes the first event in {@code model}'s filtered list from {@code model}'s event manager.
     */
    public static void deleteFirstEvent(Model model) {
        Event firstEvent = model.getFilteredEventList().get(0);
        model.deleteEvent(firstEvent);
        model.commitEventManager();
    }
}
