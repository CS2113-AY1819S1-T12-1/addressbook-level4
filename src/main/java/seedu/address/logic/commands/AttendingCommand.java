package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeywordsPredicate;

/**
 * Finds and lists all events in event manager which the user has registered for.
 */
public class AttendingCommand extends Command {

    public static final String COMMAND_WORD = "attending";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events you have registered for "
            + "and displays them as a list with index numbers.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        String currUsername = model.getUsername().toString();
        List<String> currUsernameList = new ArrayList<>();
        currUsernameList.addAll(Arrays.asList(currUsername.trim().split("\\s+")));
        Map<Prefix, List<String> > keywordsMap = new HashMap<>();
        keywordsMap.put(PREFIX_ATTENDEE, currUsernameList);
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(keywordsMap);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }
}
