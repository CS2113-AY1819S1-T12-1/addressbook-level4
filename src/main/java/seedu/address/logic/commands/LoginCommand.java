package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

/**
 * Logs user into the Event Manager Application
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs user into Event Manager. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD";

    public static final String MESSAGE_SUCCESS = "Logged in: %1$s";
    public static final String MESSAGE_LOGGED = "Already logged in!";
    public static final String MESSAGE_FAILURE = "Incorrect account credentials!";

    private final User toLogin;

    /**
     * Creates an LoginCommand to add the specified {@code User}
     */
    public LoginCommand(User user) {
        requireNonNull(user);
        toLogin = user;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.userExists(toLogin)) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        setCurrentUser(toLogin);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin.getUsername().toString()));
    }
}
