//@@author Geraldcdx
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINE;
import static seedu.address.logic.commands.ReplyCommentCommand.MESSAGE_REPLY_COMMENT;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

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
class ReplyCommentCommandTest {
    private static Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private CommentAssertion handle = new CommentAssertion();
    private ReplyCommentCommand replyCommentCommand =
            new ReplyCommentCommand(INDEX_FIRST_EVENT, VALID_LINE, VALID_COMMENT);

    @BeforeAll
    public static void setUp() {
        model = buildModel();
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
                .withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{/li}{li} (REPLY) Gerald : Hi{/li}{/ol}")
                .withTags("friends")
                .withAttendees("Scarlet Witch").build();
        User user = new UserBuilder().build();
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(user);
        model.updateEvent(model.getFilteredEventList().get(0), editedEvent);
        model.commitEventManager();
        replyCommentCommand.execute(model, commandHistory);
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
    public void replyComment_testingInput() throws CommandException {
        model = buildModel();
        Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        Event expectedEvent = new EventBuilder().withName("Art and Crafts")
                .withContact("Daniel Meier")
                .withPhone("87652533")
                .withEmail("cornelia@example.com")
                .withVenue("10th street")
                .withDateTime("22/10/2017 9:30")
                .withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{/li}{li} (REPLY) Gerald : Hi{/li}{/ol}")
                .withTags("friends")
                .withAttendees("Scarlet Witch").build();

        Event replyComment = replyCommentCommand.replyComment(
                model.getFilteredEventList().get(0),
                "Gerald"
        );
        assertEquals(expectedEvent, replyComment);
        assertEquals(expectedEvent.getComment(), replyComment.getComment());
    }

    /**
     * Testing for invalid index, not logged it
     * @throws CommandException
     */
    @Test
    public void execute_exceptionThrown() throws CommandException {
        //Invalid Index
        replyCommentCommand.setIndex(Index.fromOneBased(10));
        try {
            replyCommentCommand.execute(model, commandHistory);
        } catch (Exception e) {
            assertEquals(new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX).toString(), e.toString());
        }

        //Not logged in
        Model notLoggedIn = new ModelManager(getTypicalEventManager(), new UserPrefs());
        try {
            replyCommentCommand.execute(notLoggedIn, commandHistory);
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
        ReplyCommentCommand replyCommentCommand = new ReplyCommentCommand(INDEX_FIRST_EVENT, VALID_LINE, VALID_COMMENT);
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
        String expectedMessage = String.format(
                MESSAGE_REPLY_COMMENT,
                VALID_COMMENT,
                INDEX_FIRST_EVENT.getOneBased(),
                VALID_LINE
        );
        //Message correct
        assertEquals(expectedMessage, replyCommentCommand.execute(model, commandHistory).getString());
    }

    /**
     * Build a sample model to process with commands
     * @return
     */
    public static Model buildModel() {
        User user = new UserBuilder().build();
        Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
        Event event = new EventBuilder().withName("Art and Crafts")
                .withContact("Daniel Meier")
                .withPhone("87652533")
                .withEmail("cornelia@example.com")
                .withVenue("10th street")
                .withDateTime("22/10/2017 9:30")
                .withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{/li}{/ol}")
                .withTags("friends")
                .withAttendees("Scarlet Witch").build();
        model.updateEvent(model.getFilteredEventList().get(0), event);
        return model;
    }


}
