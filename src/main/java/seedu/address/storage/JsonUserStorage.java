package seedu.address.storage;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.user.User;

/**
 * A class to access Users stored in the hard disk as a JSON file.
 */
public class JsonUserStorage implements UserStorage {

    private Path filePath;

    public JsonUserStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserFilePath() {
        return filePath;
    }

    @Override
    public Optional<User> readUser() throws DataConversionException {
        return readUser(filePath);
    }

    /**
     * Similar to {@link #readUser()}
     * @param userFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    private Optional<User> readUser(Path userFilePath) throws  DataConversionException {
        return JsonUtil.readJsonFile(userFilePath, User.class);
    }
}
