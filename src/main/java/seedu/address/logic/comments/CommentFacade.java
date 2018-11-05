//@@author Geraldcdx
package seedu.address.logic.comments;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 *  A facade to access comment related classes, AddComment, DeleteComment and ReplyComment classes
 */
public class CommentFacade {

    private AddComment add = new AddComment();
    private DeleteComment delete = new DeleteComment();
    private ReplyComment reply = new ReplyComment();

    /**
    *  Add comment function
     * @param input comments section
     * @param comment to add
     * @param username to add to comment
     * @return String to be stored in eventManager.xml
    */
    public String addComment(String input, String comment, String username) {
        add.initComments(input);
        return add.addComment(comment, username);
    }

    /**
    *  Delete comment function
     * @param input comment section
     * @param line to delete in comment section
     * @return String to be stored in eventManager.xml
    */
    public String deleteComment(String input, int line) throws CommandException {
        delete.initComments(input);
        return delete.deleteComment(line);
    }

    /**
    *  Reply comment function
     * @param input comment section
     * @param comment to add to comment section
     * @param username to add to comment
     * @return String to be stored in eventManager.xml
    */
    public String replyComment(String input, String comment, int line, String username) throws CommandException {
        reply.initComments(input);
        return reply.replyComment(comment, line, username);
    }
}
