package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.collections.ObservableList;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.CuType;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.AttendanceContainsUserPredicate;
import seedu.address.model.event.Event;
import seedu.address.model.user.Username;

/**
 * Use to export list of current user
 * registered events as a iCalendar file
 */
public class ExportCalendarCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " filename\n"
            + "export current user registered event as an iCalender file\n"
            + "filename should not be empty or longer than 255 character\n"
            + "Example: export myCalendar";

    public static final String MESSAGE_EXPORT_SUCCESS =
            "%1$d event(s) that you registered for has been successfully exported to your %2$s.ics";

    public static final String MESSAGE_FILE_ERROR = "File %1$s.ics has existed in other folder\n"
            + "or file has errors and cannot be opened";

    public static final String MESSAGE_ZERO_EVENT_REGISTERED = "User %1$s has not registered for any event";

    private static final String CALENDAR_FILE_PATH = "data/";

    private final String fileName;

    public ExportCalendarCommand(String filename) {
        fileName = filename;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        Username currentUser = model.getUsername();
        ObservableList<Event> registeredEventList = model.getAttendingEventList(currentUser);

        //Check if no event has been registered
        if (registeredEventList.size() <= 0) {
            return new CommandResult(String.format(MESSAGE_ZERO_EVENT_REGISTERED, currentUser.value));
        }

        try {
            exportICalenderFile(registeredEventList, fileName);
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_FILE_ERROR, fileName));
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, model.getFilteredEventList().size(), fileName));
    }

    //*****************************Method related to the new export calendar command********************************
    /**
     * Get the current list of event that the current user
     * @param  model current Event Manager model
     * @param  currentUser current User
     * @return an user registered event list
     */
    public static ObservableList<Event> getAttendingEventList(Model model, Username currentUser)
            throws CommandException {
        requireAllNonNull(model, currentUser);
        model.updateFilteredEventList(new AttendanceContainsUserPredicate(currentUser));

        //No events has benn register
        if (model.getFilteredEventList().size() <= 0) {
            throw new CommandException(MESSAGE_ZERO_EVENT_REGISTERED);
        }

        return model.getFilteredEventList();
    }

    /**
     * Convert the Event in Event Manager to VEvent type in ical4j to add to iCalendar file
     * @param  registeredEventList current user registered event
     * @return a list of VEvent after conversion
     * NOTE: Currently, due to the limit of java.util.Date so event are default to last from start time to end of the
     * day
     */
    public List<VEvent> convertEventListToVEventList(ObservableList<Event> registeredEventList) {
        List<VEvent> calendarEvents =  new ArrayList<>();

        for (Event event : registeredEventList) {
            VEvent toAddEvent = convertEventToVEvent(event);
            calendarEvents.add(toAddEvent);
        }

        return calendarEvents;
    }

    /**
     * Convert Event to VEvent
     * @param  event an Event in Event Manager
     * @return a VEvent with given properties in event
     */
    public VEvent convertEventToVEvent(Event event) {
        VEvent vEvent = new VEvent(new net.fortuna.ical4j.model.DateTime(event.getDateTime().dateTime),
                new net.fortuna.ical4j.model.DateTime(event.getDateTime().dateTime.getTime() + 1000 * 60 * 60),
                event.getName().toString());

        //Event properties
        Location eventLocation = new Location(event.getVenue().value);
        Uid eventUid;
        try {
            eventUid = new Uid(UUID.randomUUID() + "@" + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            UidGenerator eventUidG = Uid::new;
            eventUid = eventUidG.generateUid();
        }

        Organizer eventOrganizer = new Organizer();
        eventOrganizer.setCalAddress(URI.create("mailto:" + event.getEmail().value));
        eventOrganizer.getParameters().add(new Cn(event.getContact().fullContactName));

        //Host properties
        Attendee eventHost = new Attendee(URI.create("mailto:" + event.getEmail().value));
        eventHost.getParameters().add(CuType.INDIVIDUAL);
        eventHost.getParameters().add(Role.REQ_PARTICIPANT);
        eventHost.getParameters().add(new Cn(event.getContact().fullContactName));
        eventHost.getParameters().add(PartStat.ACCEPTED);
        //TODO: add description when include, add current user as an attendee

        vEvent.getProperties().add(eventOrganizer);
        vEvent.getProperties().add(eventUid);
        vEvent.getProperties().add(eventLocation);
        vEvent.getProperties().add(eventHost);
        vEvent.getProperties().add(Status.VEVENT_CONFIRMED);

        return vEvent;
    }

    /**
     * Stream and write data from event to an iCalendar file will user specify file name
     * @param  eventsList user registered event list
     * @param  filename user preferences file name
     * @return userCalendar
     */
    private Calendar writeToUserCalendar(ObservableList<Event> eventsList, String filename) {
        Calendar userCalendar = buildCalendar(filename);
        List<VEvent> userRegisteredEvents = convertEventListToVEventList(eventsList);

        for (VEvent userRegisteredEvent : userRegisteredEvents) {
            userCalendar.getComponents().add(userRegisteredEvent);
        }

        return userCalendar;
    }

    /**
     * Build an Calendar with user preferences file name
     * @param filename user preferences file name
     * @return Calendar
     */
    private Calendar buildCalendar(String filename) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(String.format("-//%1$s//iCal4j 1.0//EN", filename)));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        return calendar;
    }

    /**
     * Export an iCalendar from user registered event list
     *
     * @param registeredEventList
     * @param  fileName user preferences file name
     * @throws IOException when file stream have problems
     */
    public void exportICalenderFile(ObservableList<Event> registeredEventList, String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(CALENDAR_FILE_PATH
                + String.format("%1$s.ics", fileName), false);
        CalendarOutputter outPutter = new CalendarOutputter();

        Calendar calendar = writeToUserCalendar(registeredEventList, fileName);

        outPutter.output(calendar, fileOut);
    }
}

