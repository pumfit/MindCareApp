package com.teamopendata.mindcareapp.ui.records.model;


import com.teamopendata.mindcareapp.ui.records.RecordsAdapter;

public class RecordItem {
    Object item;
    RecordsAdapter.Type type;

    public RecordItem(Object item, RecordsAdapter.Type type) {
        this.item = item;
        this.type = type;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public RecordsAdapter.Type getType() {
        return type;
    }

    public void setType(RecordsAdapter.Type type) {
        this.type = type;
    }
}
