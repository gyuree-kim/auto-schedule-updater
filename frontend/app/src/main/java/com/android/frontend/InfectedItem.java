package com.android.frontend;


//const event = new Schema({
//        date: String,
//        time: String,
//        location: String,
//        createdAt: Date,
//        updatedAt: Date

import java.util.Date;

//infected
//num: int
//        });
public class InfectedItem {

    private int type; //아이템 타입 구분
    //공통사항
    private String location;
    private Date createdAt;
    //event
    private String date;
    private String time;
    //infected
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
