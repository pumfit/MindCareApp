package com.teamopendata.mindcareapp.common.model.entity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.List;

@Entity(tableName = "record_table")
public class Record {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "date")
    private LocalDate date;
    private List<Task> tasks;
    public Record(long id, String title, LocalDate date, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.tasks = tasks;
    }

    @Ignore
    public Record(LocalDate date, String title, List<Task> tasks) {
        this.title = title;
        this.date = date;
        this.tasks = tasks;
    }

    @Ignore
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
        if (tasks == null) {
            return "기록 제목:" + title + "날짜:";
        } else {
            return "기록 제목:" + title + "날짜:" + date.toString() + "할 일:" + tasks.toString();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}