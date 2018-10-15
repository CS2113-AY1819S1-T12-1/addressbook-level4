package seedu.address.logic.comments;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Adds comments section.
 */
public abstract class Comments {

    public static Vector v;
    public static String input;

    /**
     *  Constructor to make sure that used Vector and path is initialised
     */
    public Comments () {
        input = "C:/Users/Gerald/Desktop/test/1.html";
        v = this.parseCommentSection(input);
    }


    /**
     *  Runs a pre-processing to ensure that strings can be stored as a vector
     */
    public Vector parseCommentSection(String in) {
        String input = in;
        Vector v= new Vector();
        Document htmlfile=null;
        try {
            htmlfile = Jsoup.parse(new File(input),null);
        } catch (IOException e) {
            e.printStackTrace();// This should return an error message in case it doesn't work
        }
        Element element = htmlfile.select("ol").first();
        Elements divChildren = element.children();

        Elements detachedDivChildren = new Elements();
        for (Element elem : divChildren) {
            Element detachedChild = new Element(Tag.valueOf(elem.tagName()),
                    elem.baseUri(), elem.attributes().clone());
            detachedDivChildren.add(detachedChild);
        }
        for (Element elem : divChildren) {
            v.add(elem.ownText());
        }
        return v;
    }

    /**
     *  Rewrites String to after a change has happened
     */
    public static String rewrite(Vector v, String input) {
        String commentSection="<span>Comment Section</span>\n<ol>";
        for(int i=0;i<v.size();i++) {
            commentSection+= "\n" + "<li>" + v.get(i)+"</li>";
        }
        commentSection+="\n</ol>";
        File savingFile = new File(input);
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(savingFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.write(commentSection.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return commentSection;
    }

    /**
     * Test code.
     */
    public static void main(String[] args) {
        AddComment a = new AddComment();
        DeleteComment b = new DeleteComment();
        ReplyComment c = new ReplyComment();
        String str;
        while(true){
            System.out.println("What Comment do you want to add?");
            Scanner SCANNER = new Scanner(System.in);
            String username = SCANNER.nextLine();

            try {
                Integer.parseInt(username);
                str = b.deleteComment(Integer.parseInt(username));
                System.out.println("Comment at " + username + "deleted");
                //System.out.println(str);
            } catch(NumberFormatException e) {
                str = c.replyComment(username, 1);
                System.out.println("Comment added!!");
                //System.out.println(str);
            }
        }

    }
}