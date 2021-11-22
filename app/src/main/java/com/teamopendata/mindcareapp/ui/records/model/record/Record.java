package com.teamopendata.mindcareapp.ui.records.model.record;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;

public class Record extends RecordHeader {
    private String title;

    public Record(String title, LocalDate date) {
        super(date);
        this.title = title;
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

}
