package seedu.address.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.PasswordUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.JsonUserStorage;

/**
 * Represents user account authentication
 */
public class UserSession {

    private static final Logger logger = LogsCenter.getLogger(UserSession.class);

    private JsonUserStorage userStorage;
    private User user;
    private boolean loginStatus;
    private boolean adminStatus;

    /**
     * The constructor initializes the user with a stub username and password.
     */
    public UserSession() {
        final Path userFilePath = Paths.get("users.json");
        final Username username = new Username("admin");
        final Password password = new Password("root");
        user = new User(username, password);
        loginStatus = false;
        adminStatus = false;

        try {
            userStorage = new JsonUserStorage(userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createUser(user);
    }

    /**
     * Returns true if user exists in the JSON file.
     */
    public boolean userExists(User user) {
        String loggedUsername = user.getUsername().toString();
        Map<String, String> userAccounts = userStorage.getUserAccounts();

        return userAccounts.containsKey(loggedUsername);
    }

    /**
     * Sets the current user.
     */
    public void logUser(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();
        Map<String, String> userAccounts = userStorage.getUserAccounts();

        if (userAccounts.containsKey(loggedUsername)) {
            boolean passwordsMatch = false;
            String storedPassword = userAccounts.get(loggedUsername);

            try {
                passwordsMatch = PasswordUtil.validatePassword(loggedPassword, storedPassword);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.warning("PasswordUtil could not validate password : " + StringUtil.getDetails(e));
            }

            if (passwordsMatch) {
                this.user = user;
                loginStatus = true;
                adminStatus = loggedUsername.equals("admin");
            }
        }
    }

    /**
     * Creates a new user profile in the JSON file.
     */
    public void createUser(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();

        try {
            String encryptedPassword = PasswordUtil.getEncryptedPassword(loggedPassword);
            userStorage.createUser(loggedUsername, encryptedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.warning("PasswordUtil could not generate encrypted password : " + StringUtil.getDetails(e));
        } catch (FileNotFoundException e) {
            logger.warning("users.json not found in file path : " + StringUtil.getDetails(e));
        } catch (IOException e) {
            logger.warning("JSONUserStorage could not read or write to users.json : " + StringUtil.getDetails(e));
        }
    }

    /**
     * Returns logged in user's username.
     */
    public Username getUsername() {
        return user.getUsername();
    }

    /**
     * Clears the current user.
     */
    public void clearUser() {
        loginStatus = false;
    }

    /**
     * Returns true if user is logged in.
     */
    public boolean getLoginStatus() {
        return loginStatus;
    }

    /**
     * Returns true if admin is logged in.
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof UserSession)) {
            return false;
        }

        // state check
        UserSession other = (UserSession) obj;
        return user.equals(other.user)
                && loginStatus == other.loginStatus
                && adminStatus == other.adminStatus;
    }
}
