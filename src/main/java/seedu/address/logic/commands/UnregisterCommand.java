package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import static seedu.address.logic.commands.EditCommand.createEditedEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Event;

/**
 * Unregisters for an event identified using it's displayed index from the event manager.
 */
public class UnregisterCommand extends Command {

    public static final String COMMAND_WORD = "unregister";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unregisters for an event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNREGISTER_EVENT_SUCCESS = "Unregistered for event: %1$s";
    public static final String MESSAGE_NOT_REGISTERED = "Not registered for event.";

    private final Index targetIndex;

    public UnregisterCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Event> filteredEventList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToUnregister = filteredEventList.get(targetIndex.getZeroBased());

        String attendeeName = model.getUsername().toString();

        Set<Attendee> attendeeSet = new HashSet<>(eventToUnregister.getAttendance());

        if (!attendeeSet.remove(new Attendee(attendeeName))) {
            throw new CommandException(MESSAGE_NOT_REGISTERED);
        }

        EditEventDescriptor registerEventDescriptor = new EditEventDescriptor();
        registerEventDescriptor.setAttendees(attendeeSet);
        Event registeredEvent = createEditedEvent(eventToUnregister, registerEventDescriptor);

        model.updateEvent(eventToUnregister, registeredEvent);
        model.commitEventManager();

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_UNREGISTER_EVENT_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnregisterCommand // instanceof handles nulls
                && targetIndex.equals(((UnregisterCommand) other).targetIndex)); // state check
    }
}
