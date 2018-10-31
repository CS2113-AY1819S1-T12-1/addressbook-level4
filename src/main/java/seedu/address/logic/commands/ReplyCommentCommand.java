//@@author  Geraldcdx
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comments.ReplyComment;
import seedu.address.model.Model;
import seedu.address.model.event.Comment;
import seedu.address.model.event.Event;

/**
 * Replies a comment in the comment section of the event
 */
public class ReplyCommentCommand extends Command {

    public static final String COMMAND_WORD = "replyComment";

    public static final String MESSAGE = COMMAND_WORD + ": Replies the comment section of the event identified "
            + "by the index number used in the displayed event list "
            + "with comment and line parameters given.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINE + "LINE] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE + "91 "
            + PREFIX_COMMENT + "johndoe@example.com is here";

    public static final String MESSAGE_REPLY_COMMENT = "Comment [%1$s] replied for Event %2$s at Line %3$s";
    public static final String MESSAGE_LINE_INVALID = "Line is invalid, try again. Example: replyComment 1 L/1 C/Hello";
    public static final String MESSAGE_LINE_STRING_INVALID = "Line cannot be a string!"
            + " Example: replyComment 1 L/1 C/Hello";

    private final Index index;
    private final EditCommand.EditEventDescriptor editCommentDescriptor;
    private int line = 0;
    private String comment = null;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public ReplyCommentCommand(Index index, int line, String comment) {
        requireNonNull(index);
        requireNonNull(line);
        requireNonNull(comment);

        this.index = index;
        this.line = line;
        this.comment = comment;
        this.editCommentDescriptor = new EditCommand.EditEventDescriptor();
    }

    public String getComment() {
        return this.comment;
    }

    public int getLine() {
        return this.line;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> filteredEventList = model.getFilteredEventList();

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (index.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = filteredEventList.get(index.getZeroBased());
        ReplyComment comments = new ReplyComment(eventToEdit.getComment().toString());
        Comment newComments = new Comment(comments.replyComment(getComment(), getLine(),
                model.getUsername().toString()));
        editCommentDescriptor.setComment(newComments);
        Event editedEvent = EditCommand.createEditedEvent(eventToEdit, editCommentDescriptor);
        model.updateEvent(eventToEdit, editedEvent);
        model.commitEventManager();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_REPLY_COMMENT, getComment(), index.getOneBased(), getLine()));
    }

}
