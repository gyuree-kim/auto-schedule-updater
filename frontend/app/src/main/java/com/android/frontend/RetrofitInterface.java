package com.android.frontend;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/users/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/users/register")
    Call<Void> executeRegister (@Body HashMap<String, String> map);
}
