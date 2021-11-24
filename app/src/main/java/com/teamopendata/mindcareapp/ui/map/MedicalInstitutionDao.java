package com.teamopendata.mindcareapp.ui.map;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicalInstitutionDao {
    @Query("SELECT * FROM medicalInstitution")
    List<MedicalInstitution> getAll();

    @Insert
    void insertAll(MedicalInstitution... institutions);

    @Delete
    void delete(MedicalInstitution institutions);
}
