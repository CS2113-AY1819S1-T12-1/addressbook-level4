package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.EventManager;
import seedu.address.storage.XmlAdaptedAttendee;
import seedu.address.storage.XmlAdaptedEvent;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableEManager;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventManagerBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validEventManager.xml");
    private static final Path MISSING_EVENT_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingEventField.xml");
    private static final Path INVALID_EVENT_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidEventField.xml");
    private static final Path VALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("validEvent.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempEventManager.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Night Cycling";
    private static final String VALID_CONTACT = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_VENUE = "4th street";
    private static final String VALID_DATETIME  = "10/10/2010 10:10";
    private static final String VALID_STATUS = "COMPLETED";
    private static final String VALID_COMMENT = "{span}Comment Section{/span}{ol}{/ol}";
    private static final List<XmlAdaptedTag> VALID_TAGS =
            Collections.singletonList(new XmlAdaptedTag("friends"));
    private static final List<XmlAdaptedAttendee> VALID_ATTENDEES =
            Collections.singletonList(new XmlAdaptedAttendee("Mary Kate"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, EventManager.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, EventManager.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, EventManager.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        EventManager dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableEManager.class).toModelType();
        assertEquals(9, dataFromFile.getEventList().size());
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithMissingEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                MISSING_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(null, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithInvalidEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                INVALID_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, INVALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithValidEvent_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                VALID_EVENT_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_DATETIME, VALID_STATUS, VALID_COMMENT, VALID_TAGS, VALID_ATTENDEES);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new EventManager());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new EventManager());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableEManager dataToWrite = new XmlSerializableEManager(new EventManager());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableEManager dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableEManager.class);
        assertEquals(dataToWrite, dataFromFile);

        EventManagerBuilder builder = new EventManagerBuilder(new EventManager());
        dataToWrite = new XmlSerializableEManager(
                builder.withEvent(new EventBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableEManager.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedEvent}
     * objects.
     */
    @XmlRootElement(name = "event")
    private static class XmlAdaptedEventWithRootElement extends XmlAdaptedEvent {}
}
