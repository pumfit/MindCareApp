package com.teamopendata.mindcareapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.teamopendata.mindcareapp.ui.map.MedicalInstitution;
import com.teamopendata.mindcareapp.ui.map.MedicalInstitutionDao;

@Database(entities = {MedicalInstitution.class}, version = 1)
public abstract class mindChargeDB extends RoomDatabase {

    private static mindChargeDB INSTANCE = null;

    public abstract MedicalInstitutionDao medicalInstitutionDao();


    public static mindChargeDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    mindChargeDB.class, "medicalinstitution.db").build();
            //.createFromAsset("database/medicalinstitution.db")
            Log.d("db on","db!!!");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
