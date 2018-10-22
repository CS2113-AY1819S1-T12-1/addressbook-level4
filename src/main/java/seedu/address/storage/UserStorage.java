package seedu.address.storage;

import java.io.IOException;

import com.google.gson.JsonObject;

/**
 * Represents a storage for {@link seedu.address.model.UserAccount}.
 */
public interface UserStorage {

    /**
     * Creates a new User account.
     */
    void createUser(String username, String password) throws IOException;

    /**
     * Returns a JsonObject containing user accounts.
     */
    JsonObject getUserAccounts() throws IOException;

}
