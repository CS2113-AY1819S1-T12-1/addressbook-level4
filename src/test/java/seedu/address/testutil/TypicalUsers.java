package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME;

import seedu.address.model.user.User;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final User ALICE =  new UserBuilder().withUsername("Alice Williams")
            .withPassword("in_Wond3rl4nD!").build();

    public static final User CARL =  new UserBuilder().withUsername("Carl Sagan")
            .withPassword("is_4_*star*").build();

    public static final User DARYL =  new UserBuilder().withUsername("Daryl Jones")
            .withPassword("likes-to-eat").build();

    public static final User BOB =  new UserBuilder().withUsername(VALID_USERNAME)
            .withPassword(VALID_PASSWORD).build();
}
