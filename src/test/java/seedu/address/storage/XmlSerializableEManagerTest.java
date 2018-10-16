package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.EventManager;
import seedu.address.testutil.TypicalEvents;

public class XmlSerializableEManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableEManagerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableEManager dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableEManager.class);
        EventManager eventManagerFromFile = dataFromFile.toModelType();
        EventManager typicalPersonsEventManager = TypicalEvents.getTypicalEventManager();
        assertEquals(eventManagerFromFile, typicalPersonsEventManager);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableEManager dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableEManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableEManager dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableEManager.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableEManager.MESSAGE_DUPLICATE_EVENT);
        dataFromFile.toModelType();
    }

}
