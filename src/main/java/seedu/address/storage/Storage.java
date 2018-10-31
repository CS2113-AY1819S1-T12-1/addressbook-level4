package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.EventManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EventManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getEventManagerFilePath();

    @Override
    Optional<ReadOnlyEventManager> readEventManager() throws DataConversionException, IOException;

    @Override
    void saveEventManager(ReadOnlyEventManager eventManager) throws IOException;

    /**
     * Saves the current version of the Event Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEManagerChangedEvent(EventManagerChangedEvent emce);
}
