//@@author Geraldcdx
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comments.CommentFacade;
import seedu.address.model.Model;
import seedu.address.model.event.Comment;
import seedu.address.model.event.Event;

/**
 * Delete a comment in the comment section of the event given the line
 */
public class DeleteCommentCommand extends Command {

    public static final String COMMAND_WORD = "deleteComment";

    public static final String MESSAGE = COMMAND_WORD
            + ": Deletes a comment in the comment section of the event identified "
            + "by the index number used in the displayed event list "
            + "when the line parameter is given.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINE + "LINE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE + "91 ";

    public static final String MESSAGE_DELETE_COMMENT = "Comment deleted for Event %1$s at Line %2$s";
    public static final String MESSAGE_LINE_INVALID = "Line is invalid, try again. Example: deleteComment 1 L/2";

    private final EditCommand.EditEventDescriptor editCommentDescriptor = new EditCommand.EditEventDescriptor();
    private int line = 0;
    private Index index;

    /**
     * @param index of the event in the filtered event list to edit
     * @param line to delete comment section
     */
    public DeleteCommentCommand(Index index, int line) {
        requireNonNull(index);
        requireNonNull(line);

        this.index = index;
        this.line = line;
    }

    public int getLine() {
        return this.line;
    }
    public Index getIndex() { return this.index; }

    public void setLine(int line) { this.line = line; }

    public void setIndex(Index index) { this.index = index; }

    /**
     * Save edited information in model
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return CommandResult
     * @throws CommandException login failed, not admin, invalid index
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (!model.getAdminStatus()) {
            throw new CommandException(MESSAGE_ADMIN);
        }

        List<Event> filteredEventList = model.getFilteredEventList();

        if (index.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = filteredEventList.get(index.getZeroBased());
        Event editedEvent = deleteComment(eventToEdit);
        model.updateEvent(eventToEdit, editedEvent);
        model.commitEventManager();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_DELETE_COMMENT, index.getOneBased(), getLine()));
    }

    /**
     * Removes comment with given line parameter
     * @param eventToEdit event to edit
     * @return updated comment section
     */
    public Event deleteComment(Event eventToEdit) throws CommandException {
        CommentFacade comments = new CommentFacade();
        String deletedComment = comments.deleteComment(eventToEdit.getComment().toString(), getLine());
        editCommentDescriptor.setComment(new Comment(deletedComment));
        return EditCommand.createEditedEvent(eventToEdit, editCommentDescriptor);
    }

}
