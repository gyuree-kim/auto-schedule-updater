package com.android.frontend.sns;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.frontend.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText et_msg;
    private ListView lv;

    ArrayList<MessageSnsItem> msgItems = new ArrayList<>();    //msgresponse 클라스로 빠져야하는거 해봄
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //제목줄 제목 글씨를 닉네임으로
        getSupportActionBar().setTitle("other name");
        lv = findViewById(R.id.lv_msglist);
        btn_send = findViewById(R.id.btn_send_msg);
        et_msg = findViewById(R.id.et_msg);

        getChatResponse();

    }
    private void getChatResponse(){// Inflate the layout for this fragment
//        chatAdapter=new ChatAdapter(msgItems, getLayoutInflater());
        //리스트뷰
        //        RoomList roomAdapter = new RoomList(this, last_message,last_notread, last_sender,last_time);//한번에 넣기
//        chatAdapter = new ChatAdapter();//한번에 넣을댄 여기 인자고 this, 배열, 배열
//        lv.setAdapter(chatAdapter);
//
//        //통신
//        RetrofitClient retrofitClient = new RetrofitClient();
//        Call<UserResponse> call = retrofitClient.server.getAllUsers();
//
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                Log.d("rooms", "응답 : " + String.valueOf(response.code()));
//
//                if(response.code() == 200){
//                    Log.d("rooms", "getusers 성공 : " + String.valueOf(response.code()));
//                    UserResponse ulist = response.body();
//                    //ArrayList<UserItem> user_list = response.body();
//                    for(UserItem item : ulist.items){
//                        String result = item.getId() + "id and "+ item.getName();
//                        userAdapter.addUserItem(item);
//
//                    }
////                    if (user_list != null){
////                        if (user_list.items != null && user_list.items.size()>0){
////                            for (UserItem item : user_list.items){
////                                userAdapter.addUserItem(item);
////                            }
////                        }
////                    }
//
//                } else if (response.code() == 400) {
////                    Toast.makeText(MainActivity.this, "user not found",
////                            Toast.LENGTH_LONG).show();
//                    Log.d("rooms", "users not found" + String.valueOf(response.code()));
//                }
//            }
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Log.d("rooms","response fail"+t.toString());
//                t.printStackTrace();
//            }
//        });
        //data
//        String[] last_message={"마지막 메세지","최근 메세지","누군가 메세지","그냥 메세지"};
//        int[] last_notread={1,2,3,1};
//        String[] last_sender={"사람","고양이","새","개"};
//        String[] last_time={"20200521","20210521","20200111","20200330"};
        //-----------list view 채팅방 목록





        //drauable 넣을땐 ContextCompat.getDrawable(this,R.drawable.apple)
//        roomAdapter.addRoom("마지막 메세지", 1, "사람", "20200521");
//        roomAdapter.addRoom("최근 메세지", 2, "고양이", "20210521");
//        roomAdapter.addRoom("누군가 메세지", 3, "새", "20200111");
//        roomAdapter.addRoom("그냥 메세지", 1, "치타", "20200330");

    }
}