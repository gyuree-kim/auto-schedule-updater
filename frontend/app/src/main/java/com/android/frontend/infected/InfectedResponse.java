package com.android.frontend.infected;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfectedResponse {
    @SerializedName("InfectedList")
    public List<InfectedItem> items;
}
