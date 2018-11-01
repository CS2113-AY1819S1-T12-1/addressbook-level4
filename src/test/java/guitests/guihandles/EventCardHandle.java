package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String CONTACT_FIELD_ID = "#contact";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String DATETIME_FIELD_ID = "#dateTime";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String ATTENDANCE_FIELD_ID = "#attendance";
    private static final String COMMENT_FIELD_ID = "#comment";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label contactLabel;
    private final Label venueLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label datetimeLabel;
    private final Label statusLabel;
    private final Label commentLabel;
    private final List<Label> tagLabels;
    private final List<Label> attendanceLabels;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        contactLabel = getChildNode(CONTACT_FIELD_ID);
        venueLabel = getChildNode(VENUE_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        commentLabel = getChildNode(COMMENT_FIELD_ID);
        datetimeLabel = getChildNode(DATETIME_FIELD_ID);
        statusLabel = getChildNode(STATUS_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region attendanceContainer = getChildNode(ATTENDANCE_FIELD_ID);
        attendanceLabels = attendanceContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getContact() {
        return contactLabel.getText();
    }

    public String getVenue() {
        return venueLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDatetime() {
        return datetimeLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public String getComment() {
        return commentLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public String getTagsString() {
        List<String> tagsList = getTags();
        String tagsString = String.join(" ", tagsList);
        return tagsString;
    }

    public List<String> getAttendance() {
        return attendanceLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public String getAttendanceString() {
        List<String> attendanceList = getAttendance();
        String attendanceString = String.join("<br>", attendanceList);
        return attendanceString;
    }

    /**
     * Returns true if this handle contains {@code event}.
     */
    public boolean equals(Event event) {
        return getName().equals(event.getName().fullName)
                && getContact().equals(event.getContact().fullContactName)
                && getVenue().equals(event.getVenue().value)
                && getPhone().equals(event.getPhone().value)
                && getEmail().equals(event.getEmail().value)
                && getDatetime().equals(event.getDateTime().toString())
                && getStatus().equals(event.getStatus().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(event.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
