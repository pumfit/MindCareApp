package com.teamopendata.mindcareapp.ui.records.model.record;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;

public class RecordHeader {
    protected LocalDate date;

    public RecordHeader(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @Override
    public String toString() {
        return date.toString();
    }
}
