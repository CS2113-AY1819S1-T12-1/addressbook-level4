package seedu.address.model.event;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Tests that a {@code Event}'s {@code DateTime, Name or Tags} matches any of the keywords given.
 * field find by DateTime, Address, and Name
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final Map<Prefix, List<String> > keywords;

    public EventContainsKeywordsPredicate(Map<Prefix, List<String> > keywords) {
        this.keywords = keywords;
    }

    //TODO: update when contact is added
    @Override
    public boolean test(Event event) {
        return checkKeywordsMatchEventData(keywords.get(PREFIX_KEYWORD), event)
                && checkNameKeywordsMatchEventName(keywords.get(PREFIX_NAME), event)
                && checkContactKeywordsMatchEventContact(keywords.get(PREFIX_CONTACT), event)
                && checkEmailKeywordsMatchEventEmail(keywords.get(PREFIX_EMAIL), event)
                && checkPhoneKeywordsMatchEventPhone(keywords.get(PREFIX_PHONE), event)
                && checkVenueKeywordsMatchEventVenue(keywords.get(PREFIX_VENUE), event)
                && checkDateTimeKeywordsMatchEventDateTime(keywords.get(PREFIX_DATETIME), event)
                && checkTagKeywordsMatchEventTag(keywords.get(PREFIX_TAG), event);
    }

    /**
     * Check if any keyword in list of given keywords are contained in any fields of an event
     * @param keywords list of keywords
     * @return a boolean
     */
    public boolean checkKeywordsMatchEventData (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && (StringUtil.containsWordIgnoreCase(event.getName().fullName, keyword)
                            || StringUtil.containsWordIgnoreCase(event.getContact().fullContactName, keyword)
                            || StringUtil.containsWordIgnoreCase(event.getEmail().value, keyword)
                            || StringUtil.containsWordIgnoreCase(event.getPhone().value, keyword)
                            || StringUtil.containsWordIgnoreCase(event.getVenue().value, keyword)
                            || StringUtil.containsWordIgnoreCase(event.getDateTime().toString(), keyword)
                            || event.getTags().stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))));
    }

    public boolean checkNameKeywordsMatchEventName (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && StringUtil.containsWordIgnoreCase(event.getName().fullName, keyword));
    }

    public boolean checkContactKeywordsMatchEventContact (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && StringUtil.containsWordIgnoreCase(event.getContact().fullContactName, keyword));
    }

    public boolean checkEmailKeywordsMatchEventEmail (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && StringUtil.containsWordIgnoreCase(event.getEmail().value, keyword));
    }

    public boolean checkPhoneKeywordsMatchEventPhone (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && StringUtil.containsWordIgnoreCase(event.getPhone().value, keyword));
    }

    public boolean checkVenueKeywordsMatchEventVenue (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && StringUtil.containsWordIgnoreCase(event.getVenue().value, keyword));
    }

    public boolean checkDateTimeKeywordsMatchEventDateTime (List<String> keywords, Event event) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                        && StringUtil.containsWordIgnoreCase(event.getDateTime().toString(), keyword));
    }

    public boolean checkTagKeywordsMatchEventTag (List<String> keywords, Event event) {
        if(keywords == null) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> !keyword.isEmpty()
                    && event.getTags().stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventContainsKeywordsPredicate) other).keywords)); // state check
    }
}
