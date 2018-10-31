package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventManager;

/** Indicates the EventManager in the model has changed*/
public class EventManagerChangedEvent extends BaseEvent {

    public final ReadOnlyEventManager data;

    public EventManagerChangedEvent(ReadOnlyEventManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
