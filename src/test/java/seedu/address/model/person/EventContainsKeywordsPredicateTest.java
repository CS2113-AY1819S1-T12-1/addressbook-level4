package seedu.address.model.person;

import org.junit.Test;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.testutil.EventBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventContainsKeywordsPredicate firstPredicate = new EventContainsKeywordsPredicate(firstPredicateKeywordList);
        EventContainsKeywordsPredicate secondPredicate = new EventContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventContainsKeywordsPredicate firstPredicateCopy = new EventContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_eventDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email , but does not match name, date time and tag
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withDateTime("10/10/2010 10:10").build()));
    }
}
