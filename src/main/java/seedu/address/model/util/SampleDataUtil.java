package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventManager;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Comment;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Status;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EventManager} with sample data.
 */
public class SampleDataUtil {
    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("Basketball Session"), new Contact("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Venue("Blk 30 Geylang Street 29, #06-40"),
                    new DateTime(new String("25/11/2018 11:30")), new Status("NULL"),
                    new Comment("{span}Comment Section{/span}" + "{ol}{/ol}"), getTagSet("friends"),
                    getAttendeeSet("Alex Yeoh")),
            new Event(new Name("Night Study Session"), new Contact("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Venue("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new DateTime(new String("10/10/2018 11:30")), new Status("NULL"),
                    new Comment("{span}Comment Section{/span}" + "{ol}{/ol}"),
                    getTagSet("colleagues", "friends"), getAttendeeSet("Bernice Yu")),
            new Event(new Name("House Themed Dinner"), new Contact("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Venue("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new DateTime(new String("20/1/2018 11:30")), new Status("NULL"),
                    new Comment("{span}Comment Section{/span}" + "{ol}{/ol}"),
                    getTagSet("neighbours"), getAttendeeSet("Charlotte Oliveiro")),
            new Event(new Name("Cooking Club Tryouts"), new Contact("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Venue("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new DateTime(new String("28/2/2018 11:30")), new Status("NULL"),
                    new Comment("{span}Comment Section{/span}" + "{ol}{/ol}"),
                    getTagSet("family"), getAttendeeSet("David Li")),
            new Event(new Name("Migrant Workers Rights Sharing Session"), new Contact("Irfan Ibrahim"),
                    new Phone("92492021"), new Email("irfan@example.com"),
                    new Venue("Blk 47 Tampines Street 20, #17-35"), new DateTime(new String("5/7/2018 16:30")),
                    new Status("NULL"), new Comment("{span}Comment Section{/span}" + "{ol}{/ol}"),
                    getTagSet("classmates"), getAttendeeSet("Irfan Ibrahim")),
            new Event(new Name("Ultimate Frisbee Session"), new Contact("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Venue("Blk 45 Aljunied Street 85, #11-31"),
                    new DateTime(new String("5/7/2018 4:30")), new Status("NULL"),
                    new Comment("{span}Comment Section{/span}" + "{ol}{/ol}"), getTagSet("colleagues"),
                    getAttendeeSet("Roy Balakrishnan"))
        };
    }

    public static ReadOnlyEventManager getSampleEventManager() {
        EventManager sampleAb = new EventManager();
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a attendee set containing the list of strings given.
     */
    public static Set<Attendee> getAttendeeSet(String... strings) {
        return Arrays.stream(strings)
                .map(Attendee::new)
                .collect(Collectors.toSet());
    }

}
