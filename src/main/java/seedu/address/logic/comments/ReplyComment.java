//@@author  Geraldcdx
package seedu.address.logic.comments;

import java.util.Vector;

import seedu.address.logic.commands.ReplyCommentCommand;
import seedu.address.logic.commands.exceptions.CommandException;


/**
 *  Replies to a comment on a given line with a comment
 */
public class ReplyComment extends Comments {

    /**
     *  Replies with the comment to event Comment section of index and line
     * @param comment to add to comment section
     * @param line to add below of in comment section
     * @param username to add to comment
     * @return edited comment section
     * @throws CommandException when line is not in comment section
     */
    public String replyComment(String comment, int line, String username) throws CommandException {
        Vector commentSection;
        try {
            commentSection = getComments();
            commentSection.add(line,  " (REPLY) " + username + " : " + comment);
        } catch (Exception e) {
            throw new CommandException(ReplyCommentCommand.MESSAGE_LINE_INVALID);
        }
        return rewrite(commentSection);
    }

}
