package com.teamopendata.mindcareapp.ui.map;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MedicalInstitution {

    @PrimaryKey(autoGenerate = true)//기본키
    public long id;

    @ColumnInfo(name="name")//기관이름
    public String name;

    @ColumnInfo(name="type")//기관분류
    public String type;

    @ColumnInfo(name="grade")//평가등급
    public int grade;

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

}