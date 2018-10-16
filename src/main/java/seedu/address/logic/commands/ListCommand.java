package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import javafx.collections.ObservableList;
import seedu.address.commons.Comparators.DateTimeComparator;
import seedu.address.logic.CommandHistory;
import seedu.address.model.EventManager;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

import java.util.List;
import java.util.Observable;

/**
 * Lists all events in the event manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all events\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
