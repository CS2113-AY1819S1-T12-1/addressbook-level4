package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicatePersonException;
import seedu.address.testutil.EventBuilder;

public class EventManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventManager eventManager = new EventManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventManager.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EventManager newData = getTypicalEventManager();
        eventManager.resetData(newData);
        assertEquals(newData, eventManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two events with the same identity fields
        Event editedAlice = new EventBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Event> newEvents = Arrays.asList(ALICE, editedAlice);
        EventManagerStub newData = new EventManagerStub(newEvents);

        thrown.expect(DuplicatePersonException.class);
        eventManager.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventManager.hasEvent(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(eventManager.hasEvent(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        eventManager.addEvent(ALICE);
        assertTrue(eventManager.hasEvent(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        eventManager.addEvent(ALICE);
        Event editedAlice = new EventBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(eventManager.hasEvent(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventManager.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventManager whose events list can violate interface constraints.
     */
    private static class EventManagerStub implements ReadOnlyEventManager {
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        EventManagerStub(Collection<Event> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }

}
