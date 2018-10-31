package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADMIN_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ADMIN_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
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
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalEvents.AMY;
import static seedu.address.testutil.TypicalEvents.BOB;
import static seedu.address.testutil.TypicalEvents.CARL;
import static seedu.address.testutil.TypicalEvents.HOON;
import static seedu.address.testutil.TypicalEvents.IDA;
import static seedu.address.testutil.TypicalEvents.KEYWORD_MATCHING_TRYOUTS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.UserBuilder;

public class AddCommandSystemTest extends EventManagerSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an event without tags to a non-empty event manager, command with leading spaces and trailing spaces
         * -> added
         */
        User toLogin = new UserBuilder().withUsername(VALID_ADMIN_USERNAME).withPassword(VALID_ADMIN_PASSWORD).build();
        String command = "   " + LoginCommand.COMMAND_WORD + "  "
                + ADMIN_USERNAME_DESC + "  " + ADMIN_PASSWORD_DESC + "  ";
        assertCommandSuccess(command, toLogin);


        Event toAdd = new EventBuilder(AMY).withAttendees().build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + VENUE_DESC_AMY + DATETIME_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEvent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add an event with all fields same as another event in the event manager except name -> added */
        toAdd = new EventBuilder(AMY).withName(VALID_NAME_BOB).withAttendees().build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + DATETIME_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the event manager except contact, phone and email
         * -> added
         */
        toAdd = new EventBuilder(AMY).withContact(VALID_CONTACT_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAttendees().build();
        command = EventUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty event manager -> added */
        deleteAllEvents();
        //assertCommandSuccess(ALICE); //TODO

        /* Case: add an event with tags, command with parameters in random order -> added */
        toAdd = new EventBuilder(BOB).withAttendees().build();
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + VENUE_DESC_BOB
                + NAME_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + DATETIME_DESC_BOB + CONTACT_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add an event, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the event list before adding -> added */
        showEventsWithName(KEYWORD_MATCHING_TRYOUTS);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a event card is selected --------------------------- */

        /* Case: selects first card in the event list, add a event -> added, card selection remains unchanged */
        selectEvent(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate event -> rejected */
        command = EventUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different contact -> rejected */
        toAdd = new EventBuilder(HOON).withContact(VALID_CONTACT_BOB).build();
        command = EventUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different phone -> rejected */
        toAdd = new EventBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = EventUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different email -> rejected */
        toAdd = new EventBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = EventUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different venue -> rejected */
        toAdd = new EventBuilder(HOON).withVenue(VALID_VENUE_BOB).build();
        command = EventUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different datetime -> rejected */
        toAdd = new EventBuilder(HOON).withDateTime(VALID_DATETIME_BOB).build();
        command = EventUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different tags -> rejected */
        command = EventUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + VENUE_DESC_AMY
                + DATETIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing contact -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + VENUE_DESC_AMY
                + DATETIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + EMAIL_DESC_AMY + VENUE_DESC_AMY
                + DATETIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + VENUE_DESC_AMY
                + DATETIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing venue -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DATETIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing datetime -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DATETIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + EventUtil.getEventDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + DATETIME_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid contact -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_CONTACT_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + DATETIME_DESC_AMY;
        assertCommandFailure(command, Contact.MESSAGE_CONTACT_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + DATETIME_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC
                + VENUE_DESC_AMY + DATETIME_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid venue -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_VENUE_DESC + DATETIME_DESC_AMY;
        assertCommandFailure(command, Venue.MESSAGE_VENUE_CONSTRAINTS);

        /* Case: invalid datetime -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + INVALID_DATETIME_DESC;
        assertCommandFailure(command, DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CONTACT_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + VENUE_DESC_AMY + DATETIME_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Event toAdd) {
        assertCommandSuccess(EventUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Event)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Event)
     */
    private void assertCommandSuccess(String command, Event toAdd) {
        Model expectedModel = getModel();
        expectedModel.addEvent(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs a verification as {@code assertCommandSuccess(User)}. Executes {@code command}.
     */
    private void assertCommandSuccess(String command, User toLogin) {
        Model expectedModel = getModel();
        expectedModel.logUser(toLogin);
        String expectedResultMessage = String.format(LoginCommand.MESSAGE_SUCCESS, toLogin.getUsername().toString());

        assertCommandSuccessLogin(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Event)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccessLogin(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EventListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
