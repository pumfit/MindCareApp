package com.teamopendata.mindcareapp.converters;

import androidx.room.TypeConverter;

import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamopendata.mindcareapp.model.entity.Task;
import com.teamopendata.mindcareapp.util.Utils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public String fromTasksList(List<Task> list) {
        if (list == null) return (null);
        Type type = new TypeToken<List<Task>>() {
        }.getType();
        return new Gson().toJson(list, type);
    }

    @TypeConverter
    public List<Task> toTasksList(String value) {
        if (value == null) return (null);
        Type type = new TypeToken<List<Task>>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public long LocalDateToLong(LocalDate localDate) {
        return Utils.localDateToMilli(localDate);
    }

    @TypeConverter
    public LocalDate LongFromLocalDate(long milli) {
        return Utils.milliToLocalDate(milli);
    }
}
