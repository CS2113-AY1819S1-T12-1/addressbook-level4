package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.testutil.EventManagerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEvent(null);
    }
    //Todo: Test new get attending list method
    @Test
    public void hasPerson_personNotInEventManager_returnsFalse() {
        assertFalse(modelManager.hasEvent(ALICE));
    }

    @Test
    public void hasPerson_personInEventManager_returnsTrue() {
        modelManager.addEvent(ALICE);
        assertTrue(modelManager.hasEvent(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    @Test
    public void equals() {
        EventManager eventManager = new EventManagerBuilder().withEvent(ALICE).withEvent(BENSON).build();
        EventManager differentEventManager = new EventManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eventManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eventManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different eventManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEventManager, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        Map<Prefix, List<String> > keywordMap = new HashMap<>();
        keywordMap.put(PREFIX_KEYWORD, Arrays.asList(keywords));
        modelManager.updateFilteredEventList(new EventContainsKeywordsPredicate(keywordMap));
        assertFalse(modelManager.equals(new ModelManager(eventManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEventManagerFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(eventManager, differentUserPrefs)));
    }
}
