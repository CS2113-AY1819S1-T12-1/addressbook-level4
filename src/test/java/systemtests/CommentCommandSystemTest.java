//@@author Geraldcdx
package systemtests;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.CommandTestUtil.ADMIN_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ADMIN_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;
import static seedu.address.testutil.TypicalEvents.DANIEL;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.AddCommentCommand;
import seedu.address.logic.commands.CommentAssertion;
import seedu.address.logic.commands.DeleteCommentCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReplyCommentCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;

public class CommentCommandSystemTest extends EventManagerSystemTest {

    @Test
    public void comment() {
        Model model = getModel();

        //Login
        User toLogin = new UserBuilder().withUsername(VALID_ADMIN_USERNAME).withPassword(VALID_ADMIN_PASSWORD).build();
        String command = "   " + LoginCommand.COMMAND_WORD + "  "
                + ADMIN_USERNAME_DESC + "  " + ADMIN_PASSWORD_DESC + "  ";
        assertCommandSuccess(command, toLogin);

        //Add a comment
        Index index = INDEX_FIRST_EVENT;
        command = "   " + AddCommentCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_COMMENT + "Hi";
        Event editedEvent = new EventBuilder(DANIEL).withComment("{span}Comment Section{/span}{ol}{li}"
                + "admin : Hi{/li}{/ol}").build();
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);
        String successMessage = String.format(AddCommentCommand.MESSAGE_ADD_COMMENT, "Hi", index.getOneBased());
        assertCommandSuccess(expectedModel, model, command, successMessage, index);

        //Reply First Comment
        command = ReplyCommentCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_COMMENT + "Hello" + " "
                + PREFIX_LINE + "1";
        editedEvent = new EventBuilder(DANIEL).withComment("{span}Comment Section{/span}{ol}{li}"
                + "admin : Hi{/li}{li} (REPLY) admin : Hello{/li}{/ol}").build();
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);
        successMessage = String.format(
                ReplyCommentCommand.MESSAGE_REPLY_COMMENT, "Hello", index.getOneBased(), 1);
        assertCommandSuccess(expectedModel, model, command, successMessage, index);

        //Undo
        command = UndoCommand.COMMAND_WORD;
        successMessage = UndoCommand.MESSAGE_SUCCESS;
        editedEvent = new EventBuilder(DANIEL).withComment("{span}Comment Section{/span}{ol}{li}"
                + "admin : Hi{/li}{/ol}").build();
        expectedModel.undoEventManager();
        assertCommandSuccess(expectedModel, model, command, successMessage);

        //Redo
        command = RedoCommand.COMMAND_WORD;
        successMessage = RedoCommand.MESSAGE_SUCCESS;
        editedEvent = new EventBuilder(DANIEL).withComment("{span}Comment Section{/span}{ol}{li}"
                + "admin : Hi{/li}{li} (REPLY) admin : Hello{/li}{/ol}").build();
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);
        assertCommandSuccess(expectedModel, model, command, successMessage);

        //Delete replied comment
        command = DeleteCommentCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_LINE + "2";
        editedEvent = new EventBuilder(DANIEL).withComment("{span}Comment Section{/span}{ol}{li}"
                + "admin : Hi{/li}{/ol}").build();
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);
        successMessage = String.format(
                DeleteCommentCommand.MESSAGE_DELETE_COMMENT, index.getOneBased(), 2);
        assertCommandSuccess(expectedModel, model, command, successMessage, index);

        //index <=0 for AddComment, ReplyComments and DeleteComments
        //Line not valid for AddComment and ReplyComment
        //New Event adding deleting and replying comments

    }

    /**
     *
     * @param expectedModel
     * @param model
     * @param command
     * @param successMessage
     * @param index
     */
    private void assertCommandSuccess(Model expectedModel, Model model, String command, String successMessage,
                                      Index index) {
        expectedModel.commitEventManager();
        executeCommand(command);
        assertSelectedCardChanged(index);
        assertEquals(getResultDisplay().getText(), successMessage);
        CommentAssertion handle = new CommentAssertion();
        handle.assertSuccessModel(expectedModel, model);
    }

    /**
     *
     * @param expectedModel
     * @param model
     * @param command
     * @param successMessage
     */
    private void assertCommandSuccess(Model expectedModel, Model model, String command, String successMessage) {
        expectedModel.commitEventManager();
        executeCommand(command);
        assertEquals(getResultDisplay().getText(), successMessage);
        CommentAssertion handle = new CommentAssertion();
        handle.assertSuccessModel(expectedModel, model);
    }

    /**
     * Performs a verification as {@code assertCommandSuccess(User)}. Executes {@code command}.
     */
    private void assertCommandSuccess(String command, User toLogin) {
        Model expectedModel = getModel();
        expectedModel.logUser(toLogin);
        String expectedResultMessage = String.format(LoginCommand.MESSAGE_SUCCESS, toLogin.getUsername().toString());
        assertCommandSuccessLogin(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccessLogin(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

}
