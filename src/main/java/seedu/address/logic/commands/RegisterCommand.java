package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.logic.commands.EditCommand.createEditedEvent;
import static seedu.address.logic.commands.EditCommand.EditEventDescriptor;

import java.util.Collection;
import java.util.List;
import java.util.HashSet;
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

        if (targetIndex.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToRegister = filteredEventList.get(targetIndex.getZeroBased());

        Set<Attendee> attendeeSet = new HashSet<>((Collection) eventToRegister.getAttendees());
        attendeeSet.add(new Attendee("test")); //TODO

        EditEventDescriptor registerEventDescriptor = new EditEventDescriptor();
        registerEventDescriptor.setAttendees(attendeeSet);
        Event registeredEvent = createEditedEvent(eventToRegister, registerEventDescriptor);

        model.updateEvent(eventToRegister, registeredEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitEventManager();

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
