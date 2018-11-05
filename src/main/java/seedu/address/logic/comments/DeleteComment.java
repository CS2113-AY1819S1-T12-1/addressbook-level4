//@@author Geraldcdx
package seedu.address.logic.comments;

import java.util.Vector;

import seedu.address.logic.commands.DeleteCommentCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *  Admin only: Deletes a comment given the line of it
 */
public class DeleteComment extends Comments {

    /**
     *  Can delete comment given event Comment Section index and Line
     * @param line to delete from vector
     * @return edited comment Section
     * @throws CommandException line is not present in comment section
     */
    public String deleteComment(int line) throws CommandException {
        Vector commentSection;
        try {
            commentSection = getComments();
            commentSection.remove(line - 1);
        } catch (Exception e) {
            throw new CommandException(DeleteCommentCommand.MESSAGE_LINE_INVALID);
        }
        return rewrite(commentSection);
    }
}
