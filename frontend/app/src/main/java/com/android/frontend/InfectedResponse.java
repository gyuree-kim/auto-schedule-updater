package com.android.frontend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfectedResponse {
    @SerializedName("InfectedList")
    public List<InfectedItem> items;
}
