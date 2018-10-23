package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a user's username credential.
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
