package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEventManager newData);

    /** Returns the EventManager */
    ReadOnlyEventManager getEventManager();

    /**
     * Returns true if a user account is registered in the Event Manager.
     */
    boolean userExists(User user);

    /**
     * Returns true if a event with the same identity as {@code event} exists in the event manager.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the event manager.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the event manager.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the event manager.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the event manager.
     */
    void updateEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns true if the model has previous event manager states to restore.
     */
    boolean canUndoEventManager();

    /**
     * Returns true if the model has undone event manager states to restore.
     */
    boolean canRedoEventManager();

    /**
     * Restores the model's event manager to its previous state.
     */
    void undoEventManager();

    /**
     * Restores the model's event manager to its previously undone state.
     */
    void redoEventManager();

    /**
     * Saves the current event manager state for undo/redo.
     */
    void commitEventManager();
}
