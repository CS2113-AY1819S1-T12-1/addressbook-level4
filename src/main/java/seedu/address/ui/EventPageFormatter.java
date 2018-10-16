package seedu.address.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import seedu.address.model.event.*;
import seedu.address.model.tag.Tag;

/**
 * Formatter for Event Page HTML file
 */
public class EventPageFormatter {

    private static URI SEARCH_PAGE_PATH;
    private static String SEARCH_PAGE_STRING;
    private static final DateFormat pageDateFormat = new SimpleDateFormat("EEEEE dd-MMMMM-yyyy 'at' HH:mm a");
    public EventPageFormatter() {

    }

    /**
     * Reads Event Search Page file from provided path
     */
    private static String readFile(URI path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    /**
     * Overwrites Event Search Page file
     */
    private static void writeFile(URI path) throws IOException {
        File f = new File(SEARCH_PAGE_PATH);
        FileWriter fWriter = new FileWriter(f, false);
        fWriter.write(SEARCH_PAGE_STRING);
        fWriter.close();
    }

    /**
     * Formats Event Search Page with data from event
     */
    public static void formatEvent(Event event) throws IOException, URISyntaxException {
        SEARCH_PAGE_PATH = BrowserPanel.getSearchPageUrlWithoutName().toURI();
        SEARCH_PAGE_STRING = readFile(SEARCH_PAGE_PATH);
        SEARCH_PAGE_STRING = SEARCH_PAGE_STRING.replace(SEARCH_PAGE_STRING,
                        event.getName().toString() + "<br/>" +
                        " Venue: " + event.getAddress().toString() + "<br/>" +
                        " Time: " + pageDateFormat.format(event.getDateTime().dateTime) + "<br/>" +
                        " Phone: " + event.getPhone().toString() +
                        " Email: " + event.getEmail().toString() + "<br/>");
        writeFile(SEARCH_PAGE_PATH);
    }
}
