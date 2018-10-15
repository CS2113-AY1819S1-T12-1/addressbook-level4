package seedu.address.model.event;


import java.util.Arrays;
import java.util.List;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class DateTime {
    public final int Year;
    public final int Month;
    public final int Day;
    public final int Hour;
    public final int Minute;
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date should have all value and should be in the format\n" +
                    "YY/MM/DD/HH/MM with each is a number within\n" +
                    "Year: 0 - 3000\n" +
                    "Month: 1 - 12\n" +
                    "Date: 1 - 31\n" +
                    "Hour: 0-23\n" +
                    "Minute: 0-59\n";

    public DateTime(String dateTimeAsString){
        String[] parts = dateTimeAsString.split("/");
        Integer year = Integer.valueOf(parts[0]);
        Integer month = Integer.valueOf(parts[1]);
        Integer day = Integer.valueOf(parts[2]);
        Integer hour = Integer.valueOf(parts[3]);
        Integer minute = Integer.valueOf(parts[4]);
        //Check error
        requireAllNonNull(year, month, day, hour, minute);
        checkArgument(isValidDateTime(year, month, day, hour, minute),MESSAGE_DATETIME_CONSTRAINTS);
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minute;
    }

    public DateTime(Integer year,Integer month,Integer day,Integer hour,Integer minute) {
        requireAllNonNull(year, month, day, hour, minute);
        checkArgument(isValidDateTime(year, month, day, hour, minute),MESSAGE_DATETIME_CONSTRAINTS);
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minute;
    }

    public static boolean isValidDateTime (int year,int month,int day,int hour,int minute){
        List<Integer> monthsWith31day = Arrays.asList(1,3,5,7,8,10,12);
        if(year < 0 || year > 3000)
            return false;
        if(month < 0 || month > 12)
            return false;
        if(day < 0 || day > 31)
            return false;
        else {
            if(monthsWith31day.contains(month))
                return day != 31;
            else if(month == 2)
                return day == 28;
        }
        if(hour < 0 || hour > 23)
            return false;
        if(minute < 0 || minute > 59)
            return false;
        return true;
    }
}
