package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Registers for an event identified using it's displayed index from the event manager.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Registers for an event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REGISTER_EVENT_SUCCESS = "Registered for event: %1$s";

    private final Index targetIndex;

    public RegisterCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> filteredEventList = model.getFilteredEventList();

        if (!hasLoggedIn) {
            throw new CommandException(MESSAGE_NO_LOGIN);
        }

        if (targetIndex.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_REGISTER_EVENT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                && targetIndex.equals(((RegisterCommand) other).targetIndex)); // state check
    }
}
