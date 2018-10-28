package seedu.address.ui;

import static seedu.address.model.DateTimeManager.PAGE_DATE_FORMAT;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EventSelectionChangedEvent;
import seedu.address.model.event.Event;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE = "EventSearchPage.html";


    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(javafx.event.Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Gets the URL without parameters
     */
    public static URL getSearchPageUrlWithoutName() {
        return MainApp.class.getResource(FXML_FILE_FOLDER + SEARCH_PAGE);
    }

    /**
     * Formats HTML file path into string
     */
    private String formatEventPageUrl(Event event) {
        URL searchPage = getSearchPageUrlWithoutName();
        String searchPageString = searchPage.toString()
                + "?name=" + event.getName()
                + "&contact=" + event.getContact()
                + "&phone=" + event.getPhone()
                + "&email=" + event.getEmail()
                + "&venue=" + event.getVenue().value.replaceAll("#", "%23")
                + "&dateTime=" + PAGE_DATE_FORMAT.format(event.getDateTime().dateTime).replaceAll(" ", "%20")
                + "&tags=" + event.getTagsString()
                + "&attendance=" + event.getAttendanceString();

        return searchPageString;
    }

    /**
     * Loads a HTML file with variables passed into it
     */
    private void loadEventPage(Event event) throws MalformedURLException {
        URL searchPage = new URL(formatEventPageUrl(event));
        loadPage(searchPage.toExternalForm());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleEventSelectionChangedEvent(EventSelectionChangedEvent event) throws MalformedURLException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEventPage(event.getNewSelection());
    }
}
