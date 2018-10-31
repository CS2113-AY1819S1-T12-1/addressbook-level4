package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyEventManager;

/**
 * A class to access EventManager data stored as an xml file on the hard disk.
 */
public class XmlEManagerStorage implements EventManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEManagerStorage.class);

    private Path filePath;

    public XmlEManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventManager> readEventManager() throws DataConversionException, IOException {
        return readEventManager(filePath);
    }

    /**
     * Similar to {@link #readEventManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventManager> readEventManager(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("EventManager file "  + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableEManager xmlEventManager = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlEventManager.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEventManager(ReadOnlyEventManager eventManager) throws IOException {
        saveEventManager(eventManager, filePath);
    }

    /**
     * Similar to {@link #saveEventManager(ReadOnlyEventManager)}
     * @param eventManager
     * @param filePath location of the data. Cannot be null
     */
    public void saveEventManager(ReadOnlyEventManager eventManager, Path filePath) throws IOException {
        requireNonNull(eventManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableEManager(eventManager));
    }

}
