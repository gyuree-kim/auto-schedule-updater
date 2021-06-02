package com.android.frontend;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/user/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/user/register")
    Call<Void> executeSignup (@Body HashMap<String, String> map);
}
