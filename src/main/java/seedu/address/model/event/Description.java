package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

public class Description {
    public final String description;

    /**
     * Constructs a {@code Description}.
     */
    public Description(String eventDescription) {
        requireNonNull(eventDescription);
        description = eventDescription;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
