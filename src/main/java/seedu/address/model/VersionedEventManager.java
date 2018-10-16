package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code EventManager} that keeps track of its own history.
 */
public class VersionedEventManager extends EventManager {

    private final List<ReadOnlyEventManager> eventManagerStateList;
    private int currentStatePointer;

    public VersionedEventManager(ReadOnlyEventManager initialState) {
        super(initialState);

        eventManagerStateList = new ArrayList<>();
        eventManagerStateList.add(new EventManager(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code EventManager} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        eventManagerStateList.add(new EventManager(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        eventManagerStateList.subList(currentStatePointer + 1, eventManagerStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(eventManagerStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(eventManagerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < eventManagerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedEventManager)) {
            return false;
        }

        VersionedEventManager otherVersionedAddressBook = (VersionedEventManager) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && eventManagerStateList.equals(otherVersionedAddressBook.eventManagerStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
