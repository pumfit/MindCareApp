package com.teamopendata.mindcareapp.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Utils {
    public static String getDayString(DayOfWeek day) {
        return day.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }

}
