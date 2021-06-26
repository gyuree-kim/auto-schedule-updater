package com.android.frontend.sns;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomResponse {
    @SerializedName("chatRooms")
    public List<RoomItem> items;
}
