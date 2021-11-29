package com.teamopendata.mindcareapp.ui.map;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark")
public class BookMark {

    @PrimaryKey(autoGenerate = true)//기본키
    @ColumnInfo(name="id")
    public long id;

    @ColumnInfo(name="medi_id")
    public long medi_id;

    public BookMark(long medi_id) {
        this.medi_id = medi_id;
    }
}
