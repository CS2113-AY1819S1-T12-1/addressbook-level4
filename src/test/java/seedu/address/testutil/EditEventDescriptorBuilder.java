package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Comment;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setContact(event.getContact());
        descriptor.setPhone(event.getPhone());
        descriptor.setEmail(event.getEmail());
        descriptor.setVenue(event.getVenue());
        descriptor.setDate(event.getDateTime());
        descriptor.setComment(event.getComment());
        descriptor.setTags(event.getTags());
        descriptor.setAttendees(event.getAttendance());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code EditEventDescriptor} that we are building
     */
    public EditEventDescriptorBuilder withContact(String contact) {
        descriptor.setContact(new Contact(contact));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDateTime(String datetimeAsString) {
        descriptor.setDate(new DateTime(datetimeAsString));
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code attendees} into a {@code Set<Attendee>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withAttendees(String... attendees) {
        Set<Attendee> attendeeSet = Stream.of(attendees).map(Attendee::new).collect(Collectors.toSet());
        descriptor.setAttendees(attendeeSet);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
