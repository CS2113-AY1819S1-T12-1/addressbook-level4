/*package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;*/


/**
 * Contains integration tests (interaction with the Model) for {@code UnregisterCommand}.
 */


/*public class UnregisterCommandTest {
    private Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_RegisteredEventUnfilteredList_success() {
        Event eventToUnregister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToUnregister)
                .withAddAttendees(currUsername).build();
        model.updateEvent(eventToUnregister, registeredEvent);
        model.commitEventManager();
        UnregisterCommand unregisterCommand = new UnregisterCommand(INDEX_FIRST_EVENT);
        eventToUnregister = registeredEvent;

        String expectedMessage =
                String.format(UnregisterCommand.MESSAGE_UNREGISTER_EVENT_SUCCESS,INDEX_FIRST_EVENT.getOneBased());

        // ensure user is already registered for expected event by adding him to the event before removing
        Event unregisteredEvent = new EventBuilder(eventToUnregister)
                .withAddAttendees(currUsername)
                .withRemoveAttendees(currUsername).build();
        ModelManager expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        expectedModel.updateEvent(eventToUnregister, unregisteredEvent);
        expectedModel.commitEventManager();

        assertCommandSuccess(unregisterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_UnregisteredEventUnfilteredList_failure() {
        Event eventToRegister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        RegisterCommand registerCommand = new RegisterCommand(INDEX_FIRST_EVENT);

        // add attendee using current user username to event, regardless of whether already registered
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToRegister).withAddAttendees(currUsername).build();
        model.updateEvent(eventToRegister, registeredEvent);
        model.commitEventManager();

        assertCommandFailure(registerCommand, model, commandHistory, RegisterCommand.MESSAGE_ALREADY_REGISTERED);
    }

    @Test
    public void equals() {
        UnregisterCommand unregisterFirstCommand = new UnregisterCommand(INDEX_FIRST_EVENT);
        UnregisterCommand unregisterSecondCommand = new UnregisterCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(unregisterFirstCommand.equals(unregisterFirstCommand));

        // same values -> returns true
        UnregisterCommand unregisterFirstCommandCopy = new UnregisterCommand(INDEX_FIRST_EVENT);
        assertTrue(unregisterFirstCommand.equals(unregisterFirstCommandCopy));

        // different types -> returns false
        assertFalse(unregisterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unregisterFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(unregisterFirstCommand.equals(unregisterSecondCommand));
    }
}*/
