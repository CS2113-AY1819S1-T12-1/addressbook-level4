package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code RegisterCommand}.
 */


public class RegisterCommandTest {
    private Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        User user = new UserBuilder().build();
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
    }

    @Test
    public void execute_unregisteredEventUnfilteredList_success() {
        Event eventToRegister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        RegisterCommand registerCommand = new RegisterCommand(INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(RegisterCommand.MESSAGE_REGISTER_EVENT_SUCCESS, INDEX_FIRST_EVENT.getOneBased());

        // build new event with added attendee using current user username
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToRegister).withAddAttendees(currUsername).build();

        ModelManager expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(eventToRegister, registeredEvent);
        expectedModel.commitEventManager();

        assertCommandSuccess(registerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_registeredEventUnfilteredList_throwsCommandException() {
        Event eventToRegister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        RegisterCommand registerCommand = new RegisterCommand(INDEX_FIRST_EVENT);

        // add attendee using current user username to event, regardless of whether already registered
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToRegister).withAddAttendees(currUsername).build();
        model.updateEvent(eventToRegister, registeredEvent);
        model.commitEventManager();

        assertCommandFailure(registerCommand, model, commandHistory, RegisterCommand.MESSAGE_ALREADY_REGISTERED);
    }

    /**
     * 1. Registers for a {@code Event} from a filtered list.
     * 2. Undo the registration.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously registered event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the registration. This ensures {@code RedoCommand} registers for the event object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_unregisteredEventFilteredList_sameEventRegistered() throws Exception {
        RegisterCommand registerCommand = new RegisterCommand(INDEX_FIRST_EVENT);
        Model expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());

        showEventAtIndex(model, INDEX_SECOND_EVENT);
        Event eventToRegister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToRegister).withAddAttendees(currUsername).build();
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(eventToRegister, registeredEvent);
        expectedModel.commitEventManager();

        // register -> registers second event in unfiltered event list / first event in filtered event list
        registerCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered event list to show all persons
        expectedModel.undoEventManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(registeredEvent, model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        // redo -> registers for same second event in unfiltered event list
        expectedModel.redoEventManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RegisterCommand registerFirstCommand = new RegisterCommand(INDEX_FIRST_EVENT);
        RegisterCommand registerSecondCommand = new RegisterCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(registerFirstCommand.equals(registerFirstCommand));

        // same values -> returns true
        RegisterCommand registerFirstCommandCopy = new RegisterCommand(INDEX_FIRST_EVENT);
        assertTrue(registerFirstCommand.equals(registerFirstCommandCopy));

        // different types -> returns false
        assertFalse(registerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(registerFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(registerFirstCommand.equals(registerSecondCommand));
    }
}
