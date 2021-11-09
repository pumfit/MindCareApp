package com.teamopendata.mindcareapp.ui.home.model;

public class TaskDTO {
    private String contents;
    private boolean completed;

    public TaskDTO(String contents, boolean completed) {
        this.contents = contents;
        this.completed = completed;
    }

    public TaskDTO(String contents) {
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

    @Override
    public String toString() {
        return "TaskDTO{" +
                "contents='" + contents + '\'' +
                ", completed=" + completed +
                '}';
    }
}
