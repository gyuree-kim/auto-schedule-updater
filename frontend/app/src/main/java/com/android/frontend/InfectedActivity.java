package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.frontend.sns.appointments.ListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfectedActivity extends AppCompatActivity {
    private static final String TAG = "infected";
    private static TextView tv_sender;
    private static TextView tv_content;
    private static TextView tv_sentAt;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static String userId;
    private ListView lv_infected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infected);
        //제목줄 제목 글씨를 닉네임으로
//        getSupportActionBar().setTitle("확진자 데이터");

        //id갖고오기
        tv_sender = findViewById(R.id.tv_sender);
        tv_content = findViewById(R.id.tv_content);
        tv_sentAt = findViewById(R.id.tv_sentAt);
        lv_infected = (ListView) findViewById(R.id.lv_infected_list);
        //main activity에서 id값 받아오기
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        Log.d(TAG, "get userid from loginActivity : "+ userId);
        Toast.makeText(InfectedActivity.this, "infected] id : "+userId,Toast.LENGTH_LONG).show();
        //receiver에서 받은 문자메세지 정보 받아오기
        getReceiverIntent(intent);

        //list view 구성
        GetInfectedList();



    }
    //현재 onCreate는 실행된것, intent는 onNewIntent로 받아지기에 해결

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getReceiverIntent(intent);
    }

    public void getReceiverIntent(Intent intent){
        if(intent != null){
            String sender = intent.getStringExtra("sender");
            String content = intent.getStringExtra("content");
            Date sentAt;
            sentAt = (Date)intent.getSerializableExtra("sentAt");
            if (sentAt != null){    //받게 될 때
            String ssentAt = format.format(sentAt);
            String msg = sender+" : "+content+" : ";
            //받은거 출력
            Log.d(TAG, "infected SMS : "+ msg);
            Toast.makeText(InfectedActivity.this, "infected] receive SMS : "+msg,Toast.LENGTH_LONG).show();
                if(sender.equals("#CMAS#Sever")){   //#CMAS#Severe 안드로이드 에뮬레이터 글자수 한계로 짤려서 테스트
                    tv_sender.setText(sender);
                    tv_content.setText(content);
                    tv_sentAt.setText(ssentAt);
                    //서버로 전송
                    //PostSms(userId, content,sentAt);
                }
            }else{
//                Log.d(TAG, "date null : ");
            }

        } else{
            finish();
        }

    }


    public void BtnReadSms(View view){
        //sms read가 허가됬을때
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "sms read 허가됨");

            //문자메세지를 가르킬 커서
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
            String sender;
            String content;
            String ssentAt;
            Date sentAt;
            int i=0;
            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    //2 : sender 4(5) date, 12 content

                    sender = cursor.getString(2);
                    content = cursor.getString(12);
                    sentAt = new Date(cursor.getLong(4));
                    ssentAt = format.format(sentAt);
                    String msg = sender+content+ssentAt;
                    Log.d(TAG, "read msg : "+msg);
//                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    //화면에 보여주기

                    if(sender.equals("#CMAS#Sever")){   //#CMAS#Severe 안드로이드 에뮬레이터 글자수 한계로 짤려서 테스트
                        tv_sender.setText(sender);
                        tv_content.setText(content);
                        tv_sentAt.setText(ssentAt);
                        //서버로 전송
                        //PostSms(userId, content,sentAt);
                    }
                    // use msgData
                    i=i+1;
                } while (cursor.moveToNext()&& i<3);// && i<1 최신 몇번째까지 읽을지 선택
            } else {
                // empty box, no SMS
            }

        }
    }
    public void PostSms(String userId, String content, Date sentAt){
        //server와 연결
        RetrofitClient retrofitClient = new RetrofitClient();
        //전달값을 MessageItem에 저장.
        MessageItem msg = new MessageItem(userId,content,sentAt);

        //excute login으로 post
        Call<Void> call = retrofitClient.server.createMessage(msg);
        //call의 결과 확인
        call.enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, String.valueOf(response.code()));
                if (response.code() == 201) {
                    Toast.makeText(InfectedActivity.this, "infected] post msg successfully", Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf(response.code()));
                } else if (response.code() == 400) {
                    Toast.makeText(InfectedActivity.this, "infected] both content and sentAt are required",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf(response.code()));
                } else if (response.code() == 404) {
                    Toast.makeText(InfectedActivity.this, "infected] ser not found",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf(response.code()));
                }else if (response.code() == 500) {
                    Log.d(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(InfectedActivity.this, "infected] respond fail "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("infected","response fail");
            }
        });
    }
    private void GetInfectedList(){// Inflate the layout for this fragment
        //adapter생성
        InfectedAdapter infectedAdapter = new InfectedAdapter();;
        lv_infected.setAdapter(infectedAdapter);

        //통신
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<InfectedResponse> call = retrofitClient.server.getAllEvents(userId);


        call.enqueue(new Callback<InfectedResponse>() {
            @Override
            public void onResponse(Call<InfectedResponse> call, Response<InfectedResponse> response) {
                Log.d(TAG, "응답 : " + String.valueOf(response.code()));

                if(response.code() == 200){
                    Log.d(TAG, "get events 성공 : " + String.valueOf(response.code()));
                    InfectedResponse ilist = response.body();
                    //ArrayList<UserItem> user_list = response.body();
                    for(InfectedItem item : ilist.items){
                        String result = item.getType() + "type and "+ item.getLocation();
                        infectedAdapter.addInfectedItem(item);

                    }
//                    if (user_list != null){
//                        if (user_list.items != null && user_list.items.size()>0){
//                            for (UserItem item : user_list.items){
//                                userAdapter.addUserItem(item);
//                            }
//                        }
//                    }

                } else if (response.code() == 400) {
                    Log.d(TAG, ""+String.valueOf(response.code()));
                }else if (response.code() == 404) {
                    Log.d(TAG, "events not found" + String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<InfectedResponse> call, Throwable t) {
                Log.d(TAG,"response fail"+t.toString());
                t.printStackTrace();
            }
        });


        //drauable 넣을땐 ContextCompat.getDrawable(this,R.drawable.apple)
//아이템 임의 추가
        infectedAdapter.addItem("일원동 허브노래 연습장","03.01-03.02", "15:30-20:00");
        infectedAdapter.addItem("남산타운5상가 1층 탑헤어","03.02", " ");
        infectedAdapter.addItem("서울",138);
        infectedAdapter.addItem("GS25편의점(산삼체육관역점)","03.01-03.03", "14:30-21:30");
        infectedAdapter.addItem("산림 삼포스포렉스","03.02-03.03", " ");
        infectedAdapter.addItem("청천동 철원양평해장국","03.03", "12:00-13:30");
        infectedAdapter.addItem("왕십리",8);
        infectedAdapter.addItem("일원동 허브노래 연습장","03.01-03.02", "15:30-20:00");
        infectedAdapter.addItem("남산타운5상가 1층 탑헤어","03.02", " ");
        infectedAdapter.addItem("서울",138);
        infectedAdapter.addItem("GS25편의점(산삼체육관역점)","03.01-03.03", "14:30-21:30");
        infectedAdapter.addItem("산림 삼포스포렉스","03.02-03.03", " ");
        infectedAdapter.addItem("청천동 철원양평해장국","03.03", "12:00-13:30");
        infectedAdapter.addItem("왕십리",8);
    }

}