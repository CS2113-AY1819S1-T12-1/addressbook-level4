package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventManager;
import seedu.address.model.Model;

/**
 * Clears the event manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Event manager has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (!model.getAdminStatus()) {
            throw new CommandException(MESSAGE_ADMIN);
        }

        model.resetData(new EventManager());
        model.commitEventManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
