package com.teamopendata.mindcareapp.ui.map;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicalinstitution")
public class MedicalInstitution {

    public MedicalInstitution(long id, @NonNull String name, @NonNull String type, String grade, @NonNull String address, @NonNull String tel, @NonNull String url, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.grade = grade;
        this.address = address;
        this.tel = tel;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MedicalInstitution(@NonNull String name, @NonNull String type, String grade, @NonNull String address, @NonNull String tel, @NonNull String url, double latitude, double longitude) {
        this.name = name;
        this.type = type;
        this.grade = grade;
        this.address = address;
        this.tel = tel;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MedicalInstitution() {
    }

    @PrimaryKey(autoGenerate = true)//기본키
    public long id;

    @ColumnInfo(name="name")//기관이름
    public String name;

    @ColumnInfo(name="type")//기관분류
    public String type;

    @ColumnInfo(name="grade")//평가등급
    public String grade;

    @ColumnInfo(name="address")//주소
    public String address;

    @ColumnInfo(name="tel")//전화번호
    public String tel;

    @ColumnInfo(name="url")//홈페이지
    public String url;

    @ColumnInfo(name="latitude")//위도
    public double latitude;

    @ColumnInfo(name="longitude")//경도
    public double longitude;

    public String keyword1;

    public String keyword2;

    public String keyword3;

    public String keyword4;

    public String keyword5;

    public String keyword6;

}