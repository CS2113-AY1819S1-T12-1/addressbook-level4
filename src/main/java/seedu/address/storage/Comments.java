package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Adds comments section.
 */
public class Comments {

    private File file = new File("src/main/resources/comments/");
    private final String commentsPath = file.getAbsolutePath();
    private int fileName = getLastFile(file);

    private int getLastFile(File folder) {
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length == 0) {
            return 0;
        }
        String lastFile = listOfFiles[listOfFiles.length - 1].getName();
        return Integer.parseInt(lastFile.substring(0, lastFile.length() - 5));
    }
    public String getFilePath() {
        return this.commentsPath;
    }
    public int getFileName() {
        return this.fileName;
    }
    public File getFile() {
        return this.file;
    }

    /**
     * Makes a Html file to store comments.
     */
    public void createHtml(String commentsDirectoryString, int index) {
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        try {
            File f = new File(commentsDirectoryString + "/" + Integer.toString(index) + ".html");
            if (f.exists()) {
                this.fileName++;
                createHtml(commentsDirectoryString, this.fileName);
                return;
            }
            fWriter = new FileWriter(commentsDirectoryString + "/" + Integer.toString(index) + ".html");
            writer = new BufferedWriter(fWriter);
            writer.write("<span>Comments Section</span>");
            writer.newLine(); //this is not actually needed for html files - can make your code more readable though
            writer.close(); //make sure you close the writer object
        } catch (Exception e) {
            //catch any exceptions here
            System.out.println("failed");
        }
    }

    /**
     *  Deletes the Html file associated with the event
     */
    public void deleteHtml(File folder, String commentsDirectory, int index) {
        File[] listOfFiles = folder.listFiles();
        if (index > listOfFiles.length) {
            System.out.println("error");
            //put exception here or a try catch
        }
        File file = new File(commentsDirectory + "/" + listOfFiles[index].getName());
        file.delete();
    }

    /**
     * Test code.
     */
    public static void main(String[] args) {
        Comments comment = new Comments();
        //comment.deleteHtml(comment.getFile(), comment.getFilePath(),7);
        //comment.createHtml(comment.getFilePath(), comment.getFileName());
    }
}
