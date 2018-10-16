package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Logs user out of the Event Manager.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "User has been logged out!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        clearCurrentUser();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
