package com.android.frontend;

import com.google.gson.annotations.SerializedName;
//roomlist 출력테스트용. 나중에 삭제
import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    @SerializedName("userList")
    public List<UserItem> items;
}
