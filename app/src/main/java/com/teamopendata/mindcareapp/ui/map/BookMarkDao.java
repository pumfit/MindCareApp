package com.teamopendata.mindcareapp.ui.map;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookMarkDao {
    @Delete
    void delete(BookMark bookMark);

    @Query("DELETE FROM bookmark WHERE :j = bookmark.medi_id")
    void deleteById(long j);

    @Query("SELECT * FROM medicalInstitution INNER JOIN bookmark ON medicalInstitution.id = bookmark.medi_id")
    List<MedicalInstitution> getBookmarkList();

    @Insert
    void insert(BookMark bookMark);

    @Query("SELECT * FROM medicalInstitution INNER JOIN bookmark ON :j = bookmark.medi_id")
    List<MedicalInstitution> isBookmarked(long j);
}
