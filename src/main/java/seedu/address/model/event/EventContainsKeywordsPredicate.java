package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code DateTime, Name or Tags} matches any of the keywords given.
 * field find by DateTime, Address, and Name
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;
    private final List<String> nameKeywords;
    private final List<String> contactKeywords;
    private final List<String> emailKeywords;
    private final List<String> phoneKeywords;
    private final List<String> addresKeywords;
    private final List<String> datetimeKeywords;
    private final List<String> tagKeywords;

    public EventContainsKeywordsPredicate(List<String> keywords,
                                          List<String> nameKeywords,
                                          List<String> contactKeywords,
                                          List<String> emailKeywords,
                                          List<String> phoneKeywords,
                                          List<String> addressKeywords,
                                          List<String> datetimeKeywords,
                                          List<String> tagKeywords) {
        this.keywords = keywords;
        this.nameKeywords = nameKeywords;
        this.contactKeywords = contactKeywords;
        this.emailKeywords = emailKeywords;
        this.phoneKeywords = phoneKeywords;
        this.addresKeywords = addressKeywords;
        this.datetimeKeywords = datetimeKeywords;
        this.tagKeywords = tagKeywords;
    }

    //TODO: update when contact is added
    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getContact().fullContactName, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getEmail().value, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(event.getDateTime().toString(), keyword)
                        || event.getTags().stream()
                                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)))
                && nameKeywords.stream()
                .anyMatch(nameKeyword -> StringUtil.containsWordIgnoreCase(event.getName().fullName, nameKeyword))
                ;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventContainsKeywordsPredicate) other).keywords)); // state check
    }
}
