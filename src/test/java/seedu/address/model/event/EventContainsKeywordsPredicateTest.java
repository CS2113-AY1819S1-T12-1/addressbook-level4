package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import seedu.address.logic.commands.FindCommandTest;
import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.EventBuilder;

public class EventContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Map<Prefix, List<String> > firstPredicateKeywordMap = new HashMap<>();
        Map<Prefix, List<String> > secondPredicateKeywordMap = new HashMap<>();

        List<String> firstPredicateKeywordPrefixList = Collections.singletonList("first");
        List<String> secondPredicateKeywordPrefixList = Collections.singletonList("first");
        List<String> secondPredicateNamePrefixList = Collections.singletonList("second");


        firstPredicateKeywordMap.put(PREFIX_KEYWORD, firstPredicateKeywordPrefixList);
        secondPredicateKeywordMap.put(PREFIX_KEYWORD, secondPredicateKeywordPrefixList);
        secondPredicateKeywordMap.put(PREFIX_NAME, secondPredicateNamePrefixList);

        EventContainsKeywordsPredicate firstPredicate =
                new EventContainsKeywordsPredicate(firstPredicateKeywordMap);
        EventContainsKeywordsPredicate secondPredicate =
                new EventContainsKeywordsPredicate(secondPredicateKeywordMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventContainsKeywordsPredicate firstPredicateCopy =
                new EventContainsKeywordsPredicate(firstPredicateKeywordMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    //As all prefixes are implement the same, test for every single prefix is not necessary
    @Test
    public void test_eventDataContainsKeywords_returnsTrue() {
        // One keyword one prefix
        EventContainsKeywordsPredicate predicate =
                FindCommandTest.preparePredicate("n/Alice");
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Multiple keywords one prefix
        predicate = FindCommandTest.preparePredicate("n/Alice Bob");
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Only one matching keyword one prefix
        predicate = FindCommandTest.preparePredicate("n/Bob Carol");
        assertTrue(predicate.test(new EventBuilder().withName("Alice Carol").build()));

        //Multiple keywords multiple same prefix
        predicate = FindCommandTest.preparePredicate("n/Alice Bob n/Carol");
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords one prefix
        predicate = FindCommandTest.preparePredicate("n/aLIce bOB");
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email, but does not match name, venue and dateTime with multiple prefixes
        predicate = FindCommandTest.preparePredicate("k/12345 e/alice@email.com");
        assertTrue(predicate.test(new EventBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withVenue("Main Street").withDateTime("10/10/2010 10:10").build()));

        // Keywords match phone prefix phone mix with keyword match email unknown prefixes
        predicate = FindCommandTest.preparePredicate("k/12345 E/alice@email.com DD/Alice T/");
        assertTrue(predicate.test(new EventBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }

    @Test
    public void test_eventDoesNotContainKeywords_returnsFalse() {
        // Zero keywords one prefix
        EventContainsKeywordsPredicate predicate = FindCommandTest.preparePredicate("k/");
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Zero keywords multiple prefixes
        predicate = FindCommandTest.preparePredicate("k/ n/ e/");
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Zero keywords multiple prefixes mix unknown prefixes
        predicate = FindCommandTest.preparePredicate("k/ n/ e/ E/ TTT/");
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = FindCommandTest.preparePredicate("n/Carol");
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        //  Matching keyword for some prefixes but not for all
        predicate = FindCommandTest.preparePredicate("k/12345 e/jonhd@email.com");
        assertFalse(predicate.test(new EventBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withVenue("Main Street").withDateTime("10/10/2010 10:10").build()));

        //  No matching keywords for known prefixes + unknown prefixes with keywords match name and datetime
        predicate = FindCommandTest.preparePredicate("p/55555 e/johnd@email.com DD/Alice TTT/10:10");
        assertFalse(predicate.test(new EventBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withDateTime("10/10/2010 10:10").build()));
    }

}
