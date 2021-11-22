package com.teamopendata.mindcareapp.ui.records.model.record;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.teamopendata.mindcareapp.ui.records.model.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class Record extends RecordHeader {
    private String title;
    private ArrayList<Task> tasks;

    public Record(String title, LocalDate date) {
        super(date);
        this.title = title;
    }

    public Record(LocalDate date, String title, ArrayList<Task> tasks) {
        super(date);
        this.title = title;
        this.tasks = tasks;
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

}
