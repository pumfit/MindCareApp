package com.teamopendata.mindcareapp.model;

import androidx.annotation.NonNull;

public class Task {

    private String contents;
    private boolean completed;

    public Task() {

    }

    public Task(String contents, boolean completed) {
        this.contents = contents;
        this.completed = completed;
    }

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

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "contents='" + contents + '\'' +
                ", completed=" + completed +
                '}';
    }

}
