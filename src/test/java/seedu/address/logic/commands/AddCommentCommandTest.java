//@@author Geraldcdx
package seedu.address.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.AddCommentCommand.MESSAGE_ADD_COMMENT;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
class AddCommentCommandTest {
    private static Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private AddCommentCommand addCommentCommand = new AddCommentCommand(INDEX_FIRST_EVENT, VALID_COMMENT);

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
                .withAttendees("Scarlet Witch")
                .build();
        String expectedMessage = String.format(AddCommentCommand.MESSAGE_ADD_COMMENT, addCommentCommand.getComment(),
                addCommentCommand.getIndex().getOneBased());
        User user = new UserBuilder().build();
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(user);
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);
        expectedModel.commitEventManager();
        assertCommandSuccess(addCommentCommand, model, commandHistory, expectedMessage, expectedModel);
        //undo -> the previous event edit
        expectedModel.undoEventManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        // redo -> same first event edited again
        expectedModel.redoEventManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Testing addComment function
     */
    @Test
    public void addComment_testingInput() {
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
        Event eventToEdit = filteredEventList.get(INDEX_FIRST_EVENT.getZeroBased());
        Event addedComment = addCommentCommand.addComment(eventToEdit, "admin");
        assertEquals(editedEvent, addedComment);
        assertEquals(editedEvent.getComment(), addedComment.getComment());

        //Testing a failed output
        addCommentCommand.setComment("LOLOLO");
        addedComment = addCommentCommand.addComment(eventToEdit, "admins");
        assertNotEquals(editedEvent.getComment(), addedComment.getComment());
    }

    /**
     * Testing for invalid index, not logged it
     * @throws CommandException
     */
    @Test
    public void execute_exceptionThrown() throws CommandException {
        //Invalid Index
        addCommentCommand.setIndex(Index.fromOneBased(10));
        try {
            addCommentCommand.execute(model, commandHistory);
        } catch (Exception e) {
            assertEquals(new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX).toString(), e.toString());
        }

        //Not logged in
        Model notLoggedIn = new ModelManager(getTypicalEventManager(), new UserPrefs());
        try {
            addCommentCommand.execute(notLoggedIn, commandHistory);
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
        AddCommentCommand addCommentCommand = new AddCommentCommand(INDEX_FIRST_EVENT, VALID_COMMENT);
        String expectedMessage = String.format(MESSAGE_ADD_COMMENT, VALID_COMMENT, INDEX_FIRST_EVENT.getOneBased());
        //Message correct
        assertEquals(expectedMessage, addCommentCommand.execute(model, commandHistory).getString());
        addCommentCommand.setComment("HELLO");
        //Comment wrong
        assertNotEquals(expectedMessage, addCommentCommand.execute(model, commandHistory).getString());
        addCommentCommand.setComment("Hi");
        addCommentCommand.setIndex(Index.fromOneBased(5));
        //Index wrong
        assertNotEquals(expectedMessage, addCommentCommand.execute(model, commandHistory).getString());
    }

}
