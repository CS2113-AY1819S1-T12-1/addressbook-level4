package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;

/**
 * Represents a storage for {@link seedu.address.model.AccountInfo}.
 */
public interface AccountInfoStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getAccountInfoFilePath();

    /**
     * Returns UserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserPrefs> readAccountInfo() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.UserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountInfo(UserPrefs userPrefs) throws IOException;

}
