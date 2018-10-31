package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.CARL;
import static seedu.address.testutil.TypicalEvents.DANIEL;
import static seedu.address.testutil.TypicalEvents.ELLE;
import static seedu.address.testutil.TypicalEvents.FIONA;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.model.user.User;
import seedu.address.testutil.UserBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private static final List<Prefix> PREFIXES = Arrays.asList(PREFIX_KEYWORD, PREFIX_NAME, PREFIX_CONTACT,
            PREFIX_EMAIL, PREFIX_PHONE, PREFIX_VENUE, PREFIX_DATETIME, PREFIX_TAG);

    //Setup model and history
    private Model model;
    private Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        User user = new UserBuilder().build();
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
        expectedModel.logUser(new UserBuilder().build());
    }

    @Test
    public void equals() {
        Map<Prefix, List<String>> keywordsMap1 = new HashMap<>();
        Map<Prefix, List<String> > keywordsMap2 = new HashMap<>();
        keywordsMap1.put(PREFIX_KEYWORD, Collections.singletonList("first"));
        keywordsMap2.put(PREFIX_KEYWORD, Collections.singletonList("second"));

        EventContainsKeywordsPredicate firstPredicate =
                new EventContainsKeywordsPredicate(keywordsMap1);
        EventContainsKeywordsPredicate secondPredicate =
                new EventContainsKeywordsPredicate(keywordsMap2);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    //***************************Testing no keyword input for prefixes*************************************************
    @Test
    public void execute_zeroKeywordOnePrefix_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeywordsPredicate predicate = preparePredicate(" k/");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_zeroKeywordMultiplePrefixes_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeywordsPredicate predicate = preparePredicate(" k/ n/ c/ d/");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_zeroKeywordMultiplePrefixesMultipleKeywordsMultiplePrefixes_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeywordsPredicate predicate = preparePredicate(" k/ t/Friends love n/Art c/ d/");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_zeroKeywordOnePrefixMultipleKeywordsMultipleSamePrefixes_oneEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventContainsKeywordsPredicate predicate = preparePredicate(" c/ c/Daniel c/");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getFilteredEventList());
    }

    //**************** Testing not include prefixes, assume no command format error ************************************
    @Test
    public void execute_unknownPrefixesWithAvailablePrefixes_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        EventContainsKeywordsPredicate predicate = preparePredicate(" n/Frisbee Music Dark l/love T/tag");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredEventList());
    }

    @Test
    public void execute_zeroKeywordKnownPrefixesWithUnknownPrefixes_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeywordsPredicate predicate = preparePredicate(" k/ n/ e/ L/ DTT/");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    //*******************************Testing multiple prefixes and keywords*********************************************
    @Test
    public void execute_multipleKeywordsOnePrefix_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        EventContainsKeywordsPredicate predicate = preparePredicate(" n/Frisbee Music Dark");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredEventList());
    }

    //NOTE: Events found are in chronological order, so matching array list should be sorted
    @Test
    public void execute_multipleKeywordsMultiplePrefixes_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventContainsKeywordsPredicate predicate = preparePredicate(" t/Friends n/Art n/Cooking d/09:30");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ALICE), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywordsMultiplePrefixes_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeywordsPredicate predicate = preparePredicate(" t/Friends n/Art c/daniel d/19:30");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    /**
     * Parses {@code userInput} into a {@code EventContainsKeywordsPredicate}.
     */
    public static EventContainsKeywordsPredicate preparePredicate(String userInput) {
        //Add space in case of missing
        userInput = " " + userInput;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_KEYWORD, PREFIX_NAME,
                PREFIX_CONTACT, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_VENUE, PREFIX_DATETIME, PREFIX_TAG);

        Map<Prefix, List<String> > keywordsMap = new HashMap<>();
        for (Prefix prefix : PREFIXES) {
            mapPrefixAndKeywords(keywordsMap, prefix, argMultimap);
        }
        return new EventContainsKeywordsPredicate(keywordsMap);
    }

    /**
     * Map presented prefix with its' list of keywords
     * @param prefixKeywordMap hash map for prefix and list of keyword
     * @param prefix prefix to map
     * @param argMultimap to check for prefix present
     */
    public static void mapPrefixAndKeywords(Map<Prefix, List<String> > prefixKeywordMap, Prefix prefix,
                                            ArgumentMultimap argMultimap) {
        if (argMultimap.getValue(prefix).isPresent())  {
            List<String> combineAllSamePrefixKeywordsList = new ArrayList<>();
            for (String singlePrefix : argMultimap.getAllValues(prefix)) {
                combineAllSamePrefixKeywordsList.addAll(Arrays.asList(singlePrefix.trim().split("\\s+")));
            }
            prefixKeywordMap.put(prefix, combineAllSamePrefixKeywordsList);
        } else {
            prefixKeywordMap.put(prefix, null);
        }
    }
}
