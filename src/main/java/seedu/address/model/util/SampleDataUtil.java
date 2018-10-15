package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventManager;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.event.Address;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EventManager} with sample data.
 */
public class SampleDataUtil {
    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Attendance(false),
                getTagSet("friends")),
            new Event(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Attendance(false),
                getTagSet("colleagues", "friends")),
            new Event(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Attendance(false),
                getTagSet("neighbours")),
            new Event(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Attendance(false),
                getTagSet("family")),
            new Event(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Attendance(false),
                getTagSet("classmates")),
            new Event(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Attendance(false),
                getTagSet("colleagues"))
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

}
