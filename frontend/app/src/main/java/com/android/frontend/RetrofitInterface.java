package com.android.frontend;
import java.util.HashMap;
<<<<<<< HEAD

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/user/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/user/register")
    Call<Void> executeSignup (@Body HashMap<String, String> map);
=======
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/api/users/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/users/register")
    Call<Void> executeRegister (@Body HashMap<String, String> map);

    @GET("/api/users/id/{id}")
    Call<UserItem> executeUser(@Path("id") String id);
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
}
