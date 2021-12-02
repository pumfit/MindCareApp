package com.teamopendata.mindcareapp.common;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.teamopendata.mindcareapp.R;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public final class Utils {
    private Utils() {
    }


    public static String getDayString(DayOfWeek day) {
        return day.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }

    public static long localDateToMilli(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDate milliToLocalDate(long millis) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).toLocalDate();
    }

    public static Toast buildSaveToast(@NonNull View view, @NonNull Context context, @NonNull LayoutInflater inflater) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(inflater.inflate(R.layout.toast_record_save, view.findViewById(R.id.toast_record_save)));
        toast.setMargin(0, 0);
        return toast;
    }

    public static String LocalDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate StringToLocalDate(String text) {
        String[] date = text.split("-");
        return LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
    }


    public static String toUpperCaseFirst(String s) {
        char[] arr = s.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public static int progressToPercent(float number) {
        if (number >= 0f && number < 25f) {
            number = 0;
        } else if (number >= 25f && number < 50f) {
            number = 25;
        } else if (number >= 50f && number < 75f) {
            number = 50;
        } else if (number >= 75f && number < 100f) {
            number = 75;
        } else {
            number = 100;
        }
        return (int) number;
    }

    public static Calendar LocalDateToCalender(LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        return calendar;
    }
}