package com.android.frontend;
import com.android.frontend.MessageItem;

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

    @GET("/api/users/")
    Call<UserResponse> getAllUsers();

    @POST("/api/messages/")
    Call<Void> createMessage(@Body MessageItem msg);

//    sns기능으로 안씀
//    @POST("/api/chatRooms/")
//    Call<Void> createRoom(@Body HashMap<String, String> map);
//
//    @GET("/api/users/chatRoomId/{chatRoomId}")
//    Call<ArrayList<MessageItem>> getMsgById(@Path("chatRoomId") String chatRoomId);

}
