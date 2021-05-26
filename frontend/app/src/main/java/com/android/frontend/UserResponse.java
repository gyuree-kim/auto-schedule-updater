package com.android.frontend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    @SerializedName("userList")
    public List<UserItem> items;
}
