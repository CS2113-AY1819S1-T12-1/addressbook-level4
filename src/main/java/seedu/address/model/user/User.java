package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a user account.
 */
public class User {

    private final Username username;
    private final Password password;
    private boolean loginStatus;

    public User(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }
}
