package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Username;

/**
 * Logs user out of the Event Manager.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "Logged out: %1$s";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        Username currentUsername = model.getUsername();
        model.clearUser();

        return new CommandResult(String.format(MESSAGE_SUCCESS, currentUsername.toString()));
    }
}
