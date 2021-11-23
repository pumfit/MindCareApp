package com.teamopendata.mindcareapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;

public class Record {
    private String title;
    private LocalDate date;
    private ArrayList<Task> tasks;

    public Record(LocalDate date, String title, ArrayList<Task> tasks) {
        this.title = title;
        this.date = date;
        this.tasks = tasks;
    }

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
        String taskString = null;
        if (tasks != null) {
            for (Task task : tasks) {
                taskString = String.join(task.toString() + "\n");
            }
        }
        return "기록 제목:" + title + "날짜:" + date.toString() + "할 일:" + taskString;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
