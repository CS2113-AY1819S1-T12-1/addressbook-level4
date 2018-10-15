package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private final Username username = new Username("stub");
    private final Password password = new Password("stub");
    private User currentUser = new User(username, password);

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    /**
     * Sets current logged in user.
     */
    void setCurrentUser(User user) {
        boolean adminStatus = user.getUsername().toString().equals("admin");

        currentUser = user;
        currentUser.setLoginStatus(true, adminStatus);
    }

    /**
     * Returns currently logged in user.
     */
    User getCurrentUser() {
        return currentUser;
    }

    /**
     * Clears current logged in user.
     */
    void clearCurrentUser() {
        currentUser.setLoginStatus(false, false);
    }
}
