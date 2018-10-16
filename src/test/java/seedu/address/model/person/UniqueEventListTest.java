package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicatePersonException;
import seedu.address.model.event.exceptions.PersonNotFoundException;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueEventList.add(ALICE);
        assertTrue(uniqueEventList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(ALICE);
        Event editedAlice = new EventBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEventList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueEventList.add(ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueEventList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueEventList.setEvent(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueEventList.add(ALICE);
        uniqueEventList.setEvent(ALICE, ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(ALICE);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueEventList.add(ALICE);
        Event editedAlice = new EventBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEventList.setEvent(ALICE, editedAlice);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedAlice);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueEventList.add(ALICE);
        uniqueEventList.setEvent(ALICE, BOB);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOB);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEventList.add(ALICE);
        uniqueEventList.add(BOB);
        thrown.expect(DuplicatePersonException.class);
        uniqueEventList.setEvent(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueEventList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueEventList.add(ALICE);
        uniqueEventList.remove(ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((UniqueEventList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueEventList.add(ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOB);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((List<Event>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(ALICE);
        List<Event> eventList = Collections.singletonList(BOB);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOB);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueEventList.setEvents(listWithDuplicateEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asUnmodifiableObservableList().remove(0);
    }
}
