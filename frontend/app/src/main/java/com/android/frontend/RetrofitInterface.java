package com.android.frontend;

import com.android.frontend.infected.InfectedResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("/api/events/eventId/{id}")    //변수 서버에 맞게 수정예정
    Call<InfectedResponse> getAllEvents(@Path("id") String id);
//    sns기능으로 안씀
//    @POST("/api/chatRooms/")
//    Call<Void> createRoom(@Body HashMap<String, String> map);
//
//    @GET("/api/users/chatRoomId/{chatRoomId}")
//    Call<ArrayList<MessageItem>> getMsgById(@Path("chatRoomId") String chatRoomId);

}
