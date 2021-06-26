package com.android.frontend.sns.appointments;

import com.google.gson.annotations.SerializedName;
import java.sql.Date;

public class Appointments {
    @SerializedName("_id")
    String userId;
    int chatRoomId;
    Date time;
    String location;

    Appointments(String userId, int chatRoomId, Date time, String location){
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.time = time;
        this.location = location;
    }

    public String getUserId(){
        return userId;
    }
    public int getChatRoomId(){ return chatRoomId; }
    public Date getTime(){
        return time;
    }
    public String getLocation(){
        return location;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}