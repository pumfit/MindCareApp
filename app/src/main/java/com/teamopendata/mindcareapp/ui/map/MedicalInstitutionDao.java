package com.teamopendata.mindcareapp.ui.map;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicalInstitutionDao {
    @Query("SELECT * FROM medicalInstitution ORDER BY ROWID LIMIT 1;")
    List<MedicalInstitution> getList();

    @Query("SELECT * FROM medicalInstitution")
    List<MedicalInstitution> getAll();

    @Query("SELECT * FROM medicalInstitution WHERE medicalinstitution.latitude > (:lat - 0.01) " +
            "AND medicalinstitution.latitude < (:lat + 0.01)" +
            "AND medicalinstitution.longitude > (:log - 0.01)"+
            "AND medicalinstitution.longitude < (:log + 0.01)")
    List<MedicalInstitution> getCurrentList(double lat,double log);

    @Insert
    void insertAll(MedicalInstitution... institutions);

    @Delete
    void delete(MedicalInstitution institutions);
}
