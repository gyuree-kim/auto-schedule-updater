package com.android.frontend.login;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    private String id;
    private String password;

    public LoginResult(String id, String password){
        this.id = id;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}
