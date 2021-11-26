package com.teamopendata.mindcareapp.common.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)

    private long id;
    private String contents;
    private boolean completed;

    @Ignore
    public Task() {

    }

    public Task(long id, String contents, boolean completed) {
        this.id = id;
        this.contents = contents;
        this.completed = completed;
    }

    @Ignore
    public Task(String contents, boolean completed) {
        this.contents = contents;
        this.completed = completed;
    }

    @Ignore
    public Task(String contents) {
        this.contents = contents;
        completed = false;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "contents='" + contents + '\'' +
                ", completed=" + completed +
                '}';
    }

}