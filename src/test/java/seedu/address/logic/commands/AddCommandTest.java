package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventManager;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new AddCommand(validEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Event validEvent = new EventBuilder().build();
        AddCommand addCommand = new AddCommand(validEvent);
        ModelStub modelStub = new ModelStubWithPerson(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Event alice = new EventBuilder().withName("Alice").build();
        Event bob = new EventBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different event -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public boolean userExists(User user) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyEventManager newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEventManager getEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEventManager() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Event event;

        ModelStubWithPerson(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Event> personsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return personsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            personsAdded.add(event);
        }

        @Override
        public void commitEventManager() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyEventManager getEventManager() {
            return new EventManager();
        }
    }

}
