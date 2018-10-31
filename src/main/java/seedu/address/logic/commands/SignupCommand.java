package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

/**
 * Creates a user in the Event Manager.
 */
public class SignupCommand extends Command {

    public static final String COMMAND_WORD = "signup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sign up for an Event Manager account. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD";

    public static final String MESSAGE_LOGGED = "Already logged in!";
    public static final String MESSAGE_SUCCESS = "Signed up: %1$s";
    public static final String MESSAGE_EXISTS = "Username already exists!";

    private final User toSignup;

    /**
     * Creates an SignupCommand to add the specified {@code User}
     */
    public SignupCommand(User user) {
        requireNonNull(user);
        toSignup = user;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGGED);
        }

        if (model.userExists(toSignup)) {
            throw new CommandException(MESSAGE_EXISTS);
        }

        model.createUser(toSignup);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSignup.getUsername().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignupCommand // instanceof handles nulls
                && toSignup.equals(((SignupCommand) other).toSignup));
    }
}
