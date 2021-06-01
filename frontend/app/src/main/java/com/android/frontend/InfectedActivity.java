package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfectedActivity extends AppCompatActivity {
    private static final String TAG = "infected";
    private static TextView tv_sender;
    private static TextView tv_content;
    private static TextView tv_sentAt;
    private static SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

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
        //main activity에서 id값 받아오기
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        Log.d(TAG, "get userid from loginActivity : "+ userId);
        Toast.makeText(InfectedActivity.this, "id : "+userId,Toast.LENGTH_LONG).show();


        getReceiverIntent(intent);



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
            if (sentAt != null){
            String ssentAt = format.format(sentAt);
            String msg = sender+" : "+content+" : ";
            //받은거 출력
            Log.d(TAG, "infected SMS : "+ msg);
            Toast.makeText(InfectedActivity.this, "infected SMS : "+msg,Toast.LENGTH_LONG).show();
            tv_sender.setText(sender);
            tv_content.setText(content);
            tv_sentAt.setText(ssentAt);
            }else{
                Log.d(TAG, "date null : ");

            }

        } else{
            finish();
        }

    }

}