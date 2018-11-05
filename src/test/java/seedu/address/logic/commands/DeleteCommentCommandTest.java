
//@@author Geraldcdx
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINE;
import static seedu.address.logic.commands.DeleteCommentCommand.MESSAGE_DELETE_COMMENT;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;


/**
 * Integration tests with User, model, undo, redo
 */
class DeleteCommentCommandTest {
    private static Model model;
    private CommentAssertion handle = new CommentAssertion();
    private CommandHistory commandHistory = new CommandHistory();
    private DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(INDEX_FIRST_EVENT, VALID_LINE);

    @BeforeAll
    public static void setUp() {
        User user = new UserBuilder().build();
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
    }

    /**
     * Complete integration test with model, undo, redo and login
     */
    @Test
    public void execute_integrationTest_success() throws CommandException {
        Event editedEvent = new EventBuilder().withName("Art and Crafts")
                .withContact("Daniel Meier")
                .withPhone("87652533")
                .withEmail("cornelia@example.com")
                .withVenue("10th street")
                .withDateTime("22/10/2017 9:30")
                .withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{/li}{/ol}")
                .withTags("friends")
                .withAttendees("Scarlet Witch").build();
        String expectedMessage = String.format(DeleteCommentCommand.MESSAGE_DELETE_COMMENT,
                deleteCommentCommand.getLine(),
                deleteCommentCommand.getIndex().getOneBased()
        );
        User user = new UserBuilder().build();
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(user);
        model.updateEvent(model.getFilteredEventList().get(0), editedEvent);
        model.commitEventManager();
        deleteCommentCommand.execute(model, commandHistory);
        handle.assertSuccessModel(expectedModel, model);
        //undo -> the previous event edit
        model.undoEventManager();
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);
        handle.assertSuccessModel(expectedModel, model);
        // redo -> same first event edited again
        model.redoEventManager();
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(user);
        handle.assertSuccessModel(expectedModel, model);

    }

    /**
     * Testing addComment function
     */
    @Test
    public void deleteComment_testingInput() throws CommandException {
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        Event editedEvent = new EventBuilder().withName("Art and Crafts")
                .withContact("Daniel Meier")
                .withPhone("87652533")
                .withEmail("cornelia@example.com")
                .withVenue("10th street")
                .withDateTime("22/10/2017 9:30")
                .withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{/li}{/ol}")
                .withTags("friends")
                .withAttendees("Scarlet Witch").build();

        List<Event> filteredEventList = expectedModel.getFilteredEventList();
        Event expectedEvent = filteredEventList.get(INDEX_FIRST_EVENT.getZeroBased());
        Event deleteComment = deleteCommentCommand.deleteComment(editedEvent);
        assertEquals(expectedEvent, deleteComment);
        assertEquals(expectedEvent.getComment(), deleteComment.getComment());

    }

    /**
     * Testing for invalid index, not logged it
     * @throws CommandException
     */
    @Test
    public void execute_exceptionThrown() throws CommandException {
        //Invalid Index
        deleteCommentCommand.setIndex(Index.fromOneBased(10));
        try {
            deleteCommentCommand.execute(model, commandHistory);
        } catch (Exception e) {
            assertEquals(new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX).toString(), e.toString());
        }

        //Not logged in
        Model notLoggedIn = new ModelManager(getTypicalEventManager(), new UserPrefs());
        try {
            deleteCommentCommand.execute(notLoggedIn, commandHistory);
        } catch (Exception e) {
            assertEquals(new CommandException(MESSAGE_LOGIN).toString(), e.toString());
        }

    }

    /**
     * Testing expected String
     * @throws CommandException
     */
    @Test
    public void execute_commandResult() throws CommandException {
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(INDEX_FIRST_EVENT, VALID_LINE);
        Event editedEvent = new EventBuilder().withName("Art and Crafts")
                .withContact("Daniel Meier")
                .withPhone("87652533")
                .withEmail("cornelia@example.com")
                .withVenue("10th street")
                .withDateTime("22/10/2017 9:30")
                .withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{/li}{li}comment 2{/li}"
                        + "{li}comment 3{li}{/ol}")
                .withTags("friends")
                .withAttendees("Scarlet Witch").build();
        model.updateEvent(model.getFilteredEventList().get(0), editedEvent);
        model.commitEventManager();
        String expectedMessage = String.format(MESSAGE_DELETE_COMMENT, VALID_LINE, INDEX_FIRST_EVENT.getOneBased());
        //Message correct
        assertEquals(expectedMessage, deleteCommentCommand.execute(model, commandHistory).getString());
    }


}
