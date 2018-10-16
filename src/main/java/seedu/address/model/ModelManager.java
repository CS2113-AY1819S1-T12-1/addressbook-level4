package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.Comparators.DateTimeComparator;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.EventManagerChangedEvent;
import seedu.address.model.event.Event;

/**
 * Represents the in-memory model of the event manager data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedEventManager versionedEManager;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given eventManager and userPrefs.
     * FilteredEvents will be default to be automatically sorted by DateTime
     */
    public ModelManager(ReadOnlyEventManager eventManager, UserPrefs userPrefs) {
        super();
        requireAllNonNull(eventManager, userPrefs);

        logger.fine("Initializing with address book: " + eventManager + " and user prefs " + userPrefs);

        versionedEManager = new VersionedEventManager(eventManager);
        filteredEvents = new FilteredList<>(sortEventList(versionedEManager.getEventList()));
    }

    //Todo: check if list is sorted by DateTime by default
    private ObservableList<Event> sortEventList(ObservableList<Event> eventList) {
        ObservableList<Event> sortedList = FXCollections.observableArrayList();
        sortedList.addAll(eventList);
        sortedList.sort(new DateTimeComparator());
        return FXCollections.unmodifiableObservableList(sortedList);
    }

    public ModelManager() {
        this(new EventManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyEventManager newData) {
        versionedEManager.resetData(newData);
        indicateEManagerChanged();
    }

    @Override
    public ReadOnlyEventManager getEventManager() {
        return versionedEManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateEManagerChanged() {
        raise(new EventManagerChangedEvent(versionedEManager));
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedEManager.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        versionedEManager.removeEvent(target);
        indicateEManagerChanged();
    }

    @Override
    public void addEvent(Event event) {
        versionedEManager.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEManagerChanged();
    }

    @Override
    public void updateEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        versionedEManager.updateEvent(target, editedEvent);
        indicateEManagerChanged();
    }

    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedEManager}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoEventManager() {
        return versionedEManager.canUndo();
    }

    @Override
    public boolean canRedoEventManager() {
        return versionedEManager.canRedo();
    }

    @Override
    public void undoEventManager() {
        versionedEManager.undo();
        indicateEManagerChanged();
    }

    @Override
    public void redoEventManager() {
        versionedEManager.redo();
        indicateEManagerChanged();
    }

    @Override
    public void commitEventManager() {
        versionedEManager.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedEManager.equals(other.versionedEManager)
                && filteredEvents.equals(other.filteredEvents);
    }

}
