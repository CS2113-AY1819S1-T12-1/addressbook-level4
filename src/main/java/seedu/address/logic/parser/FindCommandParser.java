package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final List<Prefix> PREFIXES = Arrays.asList(PREFIX_KEYWORD, PREFIX_NAME, PREFIX_CONTACT,
            PREFIX_EMAIL, PREFIX_PHONE, PREFIX_VENUE, PREFIX_DATETIME, PREFIX_TAG);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_KEYWORD, PREFIX_NAME,
                PREFIX_CONTACT, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_VENUE, PREFIX_DATETIME, PREFIX_TAG);

        if (!anyPrefixesPresent(argMultimap, PREFIX_KEYWORD, PREFIX_NAME, PREFIX_CONTACT,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_VENUE, PREFIX_DATETIME, PREFIX_TAG)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Map<Prefix, List<String> > keywordsMap = new HashMap<>();
        for (Prefix prefix : PREFIXES) {
            mapPrefixAndKeywords(keywordsMap, prefix, argMultimap);
        }
        return new FindCommand(
                new EventContainsKeywordsPredicate(keywordsMap));
    }

    /**
     * Map presented prefix with its' list of keywords
     * @param keywordMap hash map for prefix and list of keyword
     * @param prefix prefix to map
     * @param argMultimap to check for prefix present
     */
    public void mapPrefixAndKeywords(Map<Prefix, List<String> > keywordMap, Prefix prefix,
                                     ArgumentMultimap argMultimap) {
        if (argMultimap.getValue(prefix).isPresent())  {
            List<String> combineAllSamePrefixKeywordsList = new ArrayList<>();
            for (String singlePrefix : argMultimap.getAllValues(prefix)) {
                combineAllSamePrefixKeywordsList.addAll(Arrays.asList(singlePrefix.trim().split("\\s+")));
            }
            keywordMap.put(prefix, combineAllSamePrefixKeywordsList);
        } else {
            keywordMap.put(prefix, null);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
