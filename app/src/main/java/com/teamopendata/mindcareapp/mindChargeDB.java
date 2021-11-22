package com.teamopendata.mindcareapp;

import androidx.room.RoomDatabase;

@Database(entities = {MedicalInstitution.class}, version = 1)
public class mindChargeDB extends RoomDatabase {

    private static mindChargeDB INSTANCE = null;

    public abstract MedicalInstitutionDao medicalInstitutionDao();


    public static MusicDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MedicalInstitution.class, "medicalinstitution.db").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
