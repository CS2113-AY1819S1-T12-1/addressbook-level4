package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalUsers.ALICE;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.UserBuilder;

public class LoginCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void execute_successfulLogin() throws Exception {
        User user = new UserBuilder().build();
        ModelStubAcceptUser modelStubAcceptUser;
        modelStubAcceptUser = new ModelStubAcceptUser(user);

        CommandResult commandResult = new LoginCommand(user).execute(modelStubAcceptUser, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS,
                user.getUsername().toString()), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_failedLogin_noUser() throws Exception {
        User user = new UserBuilder(ALICE).build();
        User loggedUser = new UserBuilder().build();
        ModelStubAcceptUser modelStubAcceptUser;
        modelStubAcceptUser = new ModelStubAcceptUser(loggedUser);

        LoginCommand loginCommand = new LoginCommand(user);

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_FAILURE);
        loginCommand.execute(modelStubAcceptUser, commandHistory);
    }

    @Test
    public void execute_failedLogin_alreadyLogged() throws Exception {
        User user = new UserBuilder().build();
        ModelStubWithUser modelStubWithUser;
        modelStubWithUser = new ModelStubWithUser();
        LoginCommand loginCommand = new LoginCommand(user);

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_LOGGED);
        loginCommand.execute(modelStubWithUser, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void createUser(User user) {
            throw new AssertionError("This method should not be called!");
        }

        @Override
        public boolean getLoginStatus() {
            throw new AssertionError("This method should not be called!");
        }

        @Override
        public boolean getAdminStatus() {
            throw new AssertionError("This method should not be called!");
        }

        @Override
        public boolean userExists(User user) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void logUser(User user) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Username getUsername() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void clearUser() {
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
     * A Model stub that simulates a login.
     */
    private class ModelStubAcceptUser extends ModelStub {
        private User user;
        private boolean isLogged = false;

        ModelStubAcceptUser(User user) {
            requireNonNull(user);
            this.user = user;
        }

        @Override
        public boolean userExists(User user) {
            requireNonNull(user);
            return user.getUsername().equals(this.user.getUsername());
        }

        @Override
        public void logUser(User user) {
            isLogged = user.equals(this.user);
        }

        @Override
        public boolean getLoginStatus() {
            return isLogged;
        }
    }

    private class ModelStubWithUser extends ModelStub {
        private boolean isLogged;
        private boolean isPresent;

        ModelStubWithUser() {
            isLogged = true;
            isPresent = true;
        }

        @Override
        public boolean userExists(User user) {
            requireNonNull(user);
            return isPresent;
        }

        @Override
        public boolean getLoginStatus() {
            return isLogged;
        }
    }
}
