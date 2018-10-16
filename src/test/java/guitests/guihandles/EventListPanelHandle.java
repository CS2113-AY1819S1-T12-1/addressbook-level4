package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.event.Event;

/**
 * Provides a handle for {@code EventListPanel} containing the list of {@code EventCard}.
 */
public class EventListPanelHandle extends NodeHandle<ListView<Event>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Event> lastRememberedSelectedPersonCard;

    public EventListPanelHandle(ListView<Event> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code EventCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public EventCardHandle getHandleToSelectedCard() {
        List<Event> selectedEventList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedEventList.size() != 1) {
            throw new AssertionError("Event list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(EventCardHandle::new)
                .filter(handle -> handle.equals(selectedEventList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Event> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code event}.
     */
    public void navigateToCard(Event event) {
        if (!getRootNode().getItems().contains(event)) {
            throw new IllegalArgumentException("Event does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(event);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code EventCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the event card handle of a event associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public EventCardHandle getPersonCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(EventCardHandle::new)
                .filter(handle -> handle.equals(getPerson(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Event getPerson(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code EventCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<Event> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code EventCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<Event> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
