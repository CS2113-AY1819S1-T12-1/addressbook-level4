package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import guitests.guihandles.EventCardHandle;
import org.junit.Test;

import guitests.guihandles.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.event.Event;
import seedu.address.storage.XmlSerializableEManager;

public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<Event> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_EVENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private EventListPanelHandle eventListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EVENTS);

        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            Event expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_EVENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EventCardHandle expectedPerson = eventListPanelHandle.getPersonCardHandle(INDEX_SECOND_EVENT.getZeroBased());
        EventCardHandle selectedPerson = eventListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code EventListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Event> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of event cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code EventListPanel}.
     */
    private ObservableList<Event> createBackingList(int personCount) throws Exception {
        Path xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableEManager xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableEManager.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getEventList());
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<eventmanager>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<persons>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</persons>\n");
        }
        builder.append("</eventmanager>\n");

        Path manyPersonsFile = Paths.get(TEST_DATA_FOLDER + "manyPersons.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.toFile().deleteOnExit();
        return manyPersonsFile;
    }

    /**
     * Initializes {@code eventListPanelHandle} with a {@code EventListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EventListPanel}.
     */
    private void initUi(ObservableList<Event> backingList) {
        EventListPanel eventListPanel = new EventListPanel(backingList);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
