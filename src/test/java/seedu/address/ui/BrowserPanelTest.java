package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.model.DateTimeUtil.PAGE_DATE_FORMAT;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.EventSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private EventSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new EventSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a event
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.getSearchPageUrlWithoutName().toString()
                + "?name="
                + ALICE.getName().fullName.replaceAll(" ", "%20")
                + "&contact="
                + ALICE.getContact().fullContactName.replaceAll(" ", "%20")
                + "&phone="
                + ALICE.getPhone()
                + "&email="
                + ALICE.getEmail()
                + "&address="
                + ALICE.getAddress().value.replaceAll(" ", "%20").replaceAll("#", "%23")
                + "&dateTime="
                + PAGE_DATE_FORMAT.format(ALICE.getDateTime().dateTime).replaceAll(" ", "%20")
                + "&tags="
                + ALICE.getTagsString());

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
