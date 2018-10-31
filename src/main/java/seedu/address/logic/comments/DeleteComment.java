//@@author Geraldcdx
package seedu.address.logic.comments;

import java.util.Vector;

import seedu.address.logic.commands.DeleteCommentCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *  Deletes a comment given the line of it
 */
public class DeleteComment extends Comments {

    /**
     * Constructor to make sure that used Vector and path is initialised
     *
     * @param input
     */
    public DeleteComment(String input) {
        super(input);
    }

    /**
     *  Admin only: Can delete comment given event Comment Section indexx and Line
     */
    public String deleteComment(int line) throws CommandException {
        Vector comments = new Vector();
        try {
            comments = getComments();
            comments.remove(line - 1);
        } catch (Exception e) {
            throw new CommandException(DeleteCommentCommand.MESSAGE_LINE_INVALID);
        }
        return rewrite(comments);
    }
}
