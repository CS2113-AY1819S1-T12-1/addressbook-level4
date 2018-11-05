//@@author Geraldcdx
package seedu.address.logic.comments;

import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/**
 * Parent class for AddComment, Deletecomment and ReplyComment for parsing and manipulating comments section
 */
public abstract class Comments {

    private static String input;
    private static Vector comments;

    public Vector getComments() {
        return comments;
    }

    public String getInput() {
        return input;
    }

    /**
     * Reformats the parsed String from eventManager.xml to HTML
     * @param input parsed String from eventManager.xml
     * @return valid HTML
     */
    public String replaceBrackets(String input) {
        input = input.replace("{", "<");
        input = input.replace("}", ">");
        return input;
    }

    /**
     *  Runs a pre-processing on HTML to ensure that strings can be stored as a vector
     * @param input HTML comment section
     * @return a vector in comments form
     */
    public Vector parseCommentSection(String input) {
        Vector comments = new Vector();
        Document htmlfile = null;
        htmlfile = Jsoup.parse(input);
        Element element = htmlfile.select("ol").first();
        Elements divChildren = element.children();

        Elements detachedDivChildren = new Elements();
        for (Element elem : divChildren) {
            Element detachedChild = new Element(Tag.valueOf(elem.tagName()),
                    elem.baseUri(), elem.attributes().clone());
            detachedDivChildren.add(detachedChild);
        }
        for (Element elem : divChildren) {
            comments.add(elem.ownText());
        }
        return comments;
    }

    /**
     *  A intialising of comments to store input and comments vector
     * @param input parsed comment section from eventManager.xml
     */
    public void initComments(String input) {
        this.input = replaceBrackets(input);
        this.comments = this.parseCommentSection(getInput());
    }

    /**
     *  Rewrites String to after a change for a suitable format to store in eventManager.xml
     * @param commentsVector edited comment section vector
     * @return String that can be store in eventManager.xml
     */
    public static String rewrite(Vector commentsVector) {
        String comments = "{span}Comment Section{/span}{ol}";
        for (int i = 0; i < commentsVector.size(); i++) {
            comments += "{li}" + commentsVector.get(i) + "{/li}";
        }
        comments += "{/ol}";
        return comments;
    }

}
