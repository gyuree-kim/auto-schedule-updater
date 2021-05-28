package com.android.frontend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomResponse {
    @SerializedName("chatRooms")
    public List<RoomItem> items;
}
