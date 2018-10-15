package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.user.User;

/**
 * Represents a storage for {@link seedu.address.model.user.User}.
 */
public interface UserStorage {

    /**
     * Returns the file path of the User data file.
     */
    Path getUserFilePath();

    /**
     * Returns User data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<User> readUser() throws DataConversionException, IOException;

    //TODO = SAVE USER METHOD
}
