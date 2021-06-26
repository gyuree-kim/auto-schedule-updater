package com.android.frontend.infected;


//const event = new Schema({
//        date: String,
//        time: String,
//        location: String,
//        createdAt: Date,
//        updatedAt: Date

import com.google.gson.annotations.SerializedName;

import java.util.Date;

//infected
//num: int
//        });
public class InfectedItem {
    @SerializedName("id")
    private int type; //아이템 타입 구분
    //공통사항
    @SerializedName("location")
    private String location;
    @SerializedName("createdAt")
    private Date createdAt;
    //event
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    //infected
    @SerializedName("num")
    private int num;    //감염인원

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
