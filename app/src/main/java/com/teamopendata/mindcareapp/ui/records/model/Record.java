package com.teamopendata.mindcareapp.ui.records.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Record {
    private String title;

    private LocalDate date;

    public Record(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @Override
    public String toString() {
        return "기록 제목:" + title + "날짜:" + date.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
