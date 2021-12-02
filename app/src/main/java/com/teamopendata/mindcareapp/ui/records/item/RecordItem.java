package com.teamopendata.mindcareapp.ui.records.item;


import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.common.object.ItemType;
import com.teamopendata.mindcareapp.ui.records.adapter.RecordsAdapter.Header;

public class RecordItem {
    Object item;
    ItemType itemType;

    public RecordItem(Object item) {
        this.item = item;
        if (item instanceof Header) itemType = ItemType.TYPE_HEADER;
        else if (item instanceof Record) itemType = ItemType.TYPE_ITEM;
    }

    public RecordItem(ItemType itemType) {
        this.itemType = itemType;
    }

    public RecordItem(Object item, ItemType itemType) {
        this.item = item;
        this.itemType = itemType;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public ItemType getType() {
        return itemType;
    }

    public void setType(ItemType itemType) {
        this.itemType = itemType;
    }
}
