package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Comment;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser
        <EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand
     * object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONTACT, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_VENUE, PREFIX_DATETIME, PREFIX_COMMENT, PREFIX_TAG, PREFIX_ATTENDEE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEventDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            editEventDescriptor.setContact(ParserUtil.parseContact(argMultimap.getValue(PREFIX_CONTACT).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editEventDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editEventDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editEventDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editEventDescriptor.setDate(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            String defaultComments = "{span}Comment Section{/span}{ol}{/ol}";
            if (ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get()).equals(defaultComments)) {
                editEventDescriptor.setComment(new Comment(defaultComments));
            }
            else {
                throw new ParseException("C/ needs to be C/" + defaultComments + "to reset the comment section.");
            }
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editEventDescriptor::setTags);
        parseAttendeesForEdit(argMultimap.getAllValues(PREFIX_ATTENDEE)).ifPresent(editEventDescriptor::setAttendees);

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editEventDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> attendees} into a {@code Set<Attendee>} if {@code attendees} is non-empty.
     * If {@code attendees} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Attendee>} containing zero attendees.
     */
    private Optional<Set<Attendee>> parseAttendeesForEdit(Collection<String> attendees) throws ParseException {
        assert attendees != null;

        if (attendees.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> attendeeSet = attendees.size() == 1 && attendees.contains("")
                ? Collections.emptySet() : attendees;
        return Optional.of(ParserUtil.parseAttendees(attendeeSet));
    }

}
