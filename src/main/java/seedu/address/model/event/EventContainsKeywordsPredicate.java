package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import com.sun.xml.bind.v2.TODO;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code any fields} matches any of the keywords given.
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    //TODO: update when contact is added
    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(event.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getDateTime().toString(), keyword)
                        || event.getTags().stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventContainsKeywordsPredicate) other).keywords)); // state check
    }

}
