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
     * Constructor to make sure that used Vector and path is initialised
     *
     * @param input
     */
    public ReplyComment(String input) {
        super(input);
    }

    /**
     *  Replies with the comment to event Comment section of index and line
     */
    public String replyComment(String comment, int line, String username) throws CommandException {
        Vector comments = new Vector();
        try {
            comments = getComments();
            comments.add(line,  " (REPLY) " + username + " : " + comment);
        } catch (Exception e) {
            throw new CommandException(ReplyCommentCommand.MESSAGE_LINE_INVALID);
        }
        return rewrite(comments);
    }

}
