package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EventManager level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label contact;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label venue;
    @FXML
    private Label email;
    @FXML
    private Label dateTime;
    @FXML
    private Label status;
    @FXML
    //private FlowPane tags;
    private Label comment;
    @FXML
    private FlowPane attendance;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().fullName);
        contact.setText(event.getContact().fullContactName);
        phone.setText(event.getPhone().value);
        venue.setText(event.getVenue().value);
        email.setText(event.getEmail().value);
        comment.setText(event.getComment().value);
        dateTime.setText(event.getDateTime().toString());
        status.setText(event.getStatus().currentStatus);
        //event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        event.getAttendance().forEach(attendee -> attendance.getChildren().add(new Label(attendee.attendeeName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
