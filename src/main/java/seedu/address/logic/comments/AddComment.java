//@@author Geraldcdx
package seedu.address.logic.comments;

import java.util.Vector;

/**
 *  Adds Comment to the end of the comments section
 */
public class AddComment extends Comments {

    /**
     *  Appends comment to the end of the current vector and stores
     * @param comment comment to be added
     * @param username user's username
     * @return new comments section
     */
    public String addComment(String comment, String username) {
        Vector commentSection;
        commentSection = getComments();
        commentSection.add(username + " : " + comment);
        return rewrite(commentSection);
    }

}
