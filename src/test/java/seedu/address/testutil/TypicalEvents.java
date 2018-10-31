package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_HAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_TED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventManager;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    //Sorted list in order should be: CARL, ELLE, ALICE, DANIEL, FIONA, BENSON, GEORGE,
    public static final Event ALICE = new EventBuilder().withName("Inter Neighbourhood Cooking Competition")
            .withContact("Alice Pauline")
            .withVenue("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withDateTime("22/10/2018 9:30")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}")
            .withTags("friends")
            .withAttendees("Peter Parker").build();
    public static final Event BENSON = new EventBuilder().withName("Dancing Tryouts")
            .withContact("Benson Meier")
            .withVenue("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withDateTime("2/10/2018 19:30")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}")
            .withTags("owesMoney", "friends")
            .withAttendees("Mary Kate").build();

    public static final Event CARL = new EventBuilder().withName("Frisbee Competition")
            .withContact("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withVenue("wall street")
            .withDateTime("22/1/2018 9:30")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}").build();
    public static final Event DANIEL = new EventBuilder().withName("Art and Crafts")
            .withContact("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withVenue("10th street")
            .withDateTime("22/10/2017 9:30")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}")
            .withTags("friends")
            .withAttendees("Scarlet Witch").build();
    public static final Event ELLE = new EventBuilder().withName("Music Tryouts")
            .withContact("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withVenue("michegan ave")
            .withDateTime("12/8/2018 17:30")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}").build();
    public static final Event FIONA = new EventBuilder().withName("Dining In The Dark")
            .withContact("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withVenue("little tokyo")
            .withDateTime("22/10/2018 15:15")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}").build();
    public static final Event GEORGE = new EventBuilder().withName("College Games")
            .withContact("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withVenue("4th street")
            .withDateTime("30/10/2018 14:30")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}").build();

    // Manually added
    public static final Event HOON = new EventBuilder().withName("Eating Competition")
            .withContact("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withVenue("little india")
            .withDateTime("1/9/2019 20:00")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}").build();
    public static final Event IDA = new EventBuilder().withName("Yoga Session")
            .withContact("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withVenue("chicago ave")
            .withDateTime("18/10/2018 10:45")
            .withStatus("NULL")
            .withComment("{span}Comment Section{/span}{ol}{/ol}").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    // Order in the list BOB, AMY
    public static final Event AMY =
            new EventBuilder().withName(VALID_NAME_AMY).withContact(VALID_CONTACT_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withVenue(VALID_VENUE_AMY).withDateTime(VALID_DATETIME_AMY)
            .withStatus(VALID_STATUS_AMY).withComment(VALID_COMMENT_AMY).withTags(VALID_TAG_FRIEND)
            .withAttendees(VALID_ATTENDEE_TED).build();
    public static final Event BOB =
            new EventBuilder().withName(VALID_NAME_BOB).withContact(VALID_CONTACT_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withVenue(VALID_VENUE_BOB).withDateTime(VALID_DATETIME_BOB)
            .withStatus(VALID_STATUS_BOB).withComment(VALID_COMMENT_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withAttendees(VALID_ATTENDEE_TED, VALID_ATTENDEE_HAN).build();

    // Will match BENSON and ELLE
    public static final String KEYWORD_MATCHING_TRYOUTS = "Tryouts"; // A keyword that matches Tryouts

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code EventManager} with all the typical Events.
     */
    public static EventManager getTypicalEventManager() {
        EventManager ab = new EventManager();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
