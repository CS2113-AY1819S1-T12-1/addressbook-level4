package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.user.User;
import seedu.address.testutil.UserBuilder;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeFailedLogout() throws Exception {
        Model model = new ModelManager();
        LogoutCommand command = new LogoutCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Command.MESSAGE_LOGIN);
        command.execute(model, commandHistory);
    }

    @Test
    public void executeSuccessfulLogout() {
        User user = new UserBuilder().build();
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        model.logUser(user);
        expectedModel.logUser(user);
        expectedModel.clearUser();

        assertCommandSuccess(new LogoutCommand(), model, commandHistory,
                String.format(LogoutCommand.MESSAGE_SUCCESS, user.getUsername().toString()), expectedModel);
    }
}
