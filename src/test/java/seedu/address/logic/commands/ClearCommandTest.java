package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EventManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.testutil.UserBuilder;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_emptyAddressBook_success() {
        User user = new UserBuilder().build();
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        model.logUser(user);
        expectedModel.logUser(user);
        expectedModel.commitEventManager();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());

        User user = new UserBuilder().build();
        model.logUser(user);
        expectedModel.logUser(user);
        expectedModel.resetData(new EventManager());
        expectedModel.commitEventManager();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
