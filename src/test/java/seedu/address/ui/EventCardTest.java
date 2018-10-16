package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import guitests.guihandles.EventCardHandle;
import org.junit.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Event eventWithNoTags = new EventBuilder().withTags(new String[0]).build();
        EventCard eventCard = new EventCard(eventWithNoTags, 1);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, eventWithNoTags, 1);

        // with tags
        Event eventWithTags = new EventBuilder().build();
        eventCard = new EventCard(eventWithTags, 2);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, eventWithTags, 2);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new EventBuilder().withName("differentName").build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, Event expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysPerson(expectedEvent, eventCardHandle);
    }
}
