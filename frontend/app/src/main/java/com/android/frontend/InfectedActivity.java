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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfectedActivity extends AppCompatActivity {
    private static final String TAG = "infected";
    private static TextView tv_sender;
    private static TextView tv_content;
    private static TextView tv_sentAt;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

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
        Toast.makeText(InfectedActivity.this, "infected] id : "+userId,Toast.LENGTH_LONG).show();


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
            Toast.makeText(InfectedActivity.this, "infected] receive SMS : "+msg,Toast.LENGTH_LONG).show();
            tv_sender.setText(sender);
            tv_content.setText(content);
            tv_sentAt.setText(ssentAt);
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
                    tv_sender.setText(sender);
                    tv_content.setText(content);
                    tv_sentAt.setText(ssentAt);
                    // use msgData
                    i=i+1;
                } while (cursor.moveToNext());// && i<1
            } else {
                // empty box, no SMS
            }

        }
    }

}