package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommandTest;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allUnknownPrefixes_throwsParseException() {
        assertParseFailure(parser, " KF/Keywords DD/ T/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(FindCommandTest.preparePredicate("k/Alice Bob"));
        assertParseSuccess(parser, " k/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n k/Alice \n \t Bob  \t", expectedFindCommand);

        //multiple known prefixes only
        expectedFindCommand = new FindCommand(FindCommandTest.preparePredicate("k/Alice n/Bob"));
        assertParseSuccess(parser, " k/Alice n/Bob", expectedFindCommand);

        //multiple known prefixes + multiple unknown prefixes
        expectedFindCommand = new FindCommand(FindCommandTest
                .preparePredicate("k/Alice n/Bob EE/love TT/tag"));
        assertParseSuccess(parser, " k/Alice n/Bob EE/love TT/tag", expectedFindCommand);

    }
}
