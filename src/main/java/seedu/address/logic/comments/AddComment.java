//@@author Geraldcdx
package seedu.address.logic.comments;

import java.util.Vector;

/**
 *  Adds Comment to the end of the comments section
 */
public class AddComment extends Comments {

    /**
     * Constructor to make sure that used Vector and path is initialised
     *
     * @param input
     */
    public AddComment(String input) {
        super(input);
    }

    /**
     *  Appends comment to the end of the current vector and stores
     */
    public String addComment(String comment, String username) {
        Vector comments = new Vector();
        comments = getComments();
        comments.add(username + " : " + comment);
        return rewrite(comments);
    }

}
