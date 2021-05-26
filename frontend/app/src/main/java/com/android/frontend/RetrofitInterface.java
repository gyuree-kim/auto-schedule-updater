package com.android.frontend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/api/users/login")
    Call<Void> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/users/register")
    Call<Void> executeRegister (@Body HashMap<String, String> map);

    @GET("/api/users/id/{id}")
    Call<UserItem> getUserById(@Path("id") String id);

    @POST("/api/chatRooms/")
    Call<Void> createRoom(@Body HashMap<String, String> map);

    @GET("/api/users/")
    Call<UserResponse> getAllUsers();

}
