package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstEvent;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.UserBuilder;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstEvent(model);
        deleteFirstEvent(model);

        deleteFirstEvent(expectedModel);
        deleteFirstEvent(expectedModel);
        expectedModel.logUser(new UserBuilder().build());
    }

    @Test
    public void execute() {
        assertCommandSuccess(new LoginCommand(new UserBuilder().build()), model, commandHistory,
                String.format(LoginCommand.MESSAGE_SUCCESS, "admin"), expectedModel);

        // multiple undoable states in model
        expectedModel.undoEventManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoEventManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
