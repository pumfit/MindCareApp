package com.teamopendata.mindcareapp.common.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.teamopendata.mindcareapp.common.model.entity.Record;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface RecordDao {
    @Insert
    void insert(Record record);

    @Delete
    void delete(Record record);

    @Update
    void update(Record record);

    @Update()
    void updateAll(List<Record> records);

    @Query("SELECT * FROM record_table ORDER BY date")
    List<Record> getAll();

    @Query("Select * FROM record_table WHERE record_table.date LIKE :localdate")
    Record getDateRecord(LocalDate localdate);

    @Query("SELECT * FROM record_table WHERE date <= :today AND date > :yesterday")
    List<Record> getTasks(LocalDate today, LocalDate yesterday);

    @Query("SELECT date FROM record_table")
    List<LocalDate> getAllRecordDate();
}
