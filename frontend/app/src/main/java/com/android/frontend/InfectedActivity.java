package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class InfectedActivity extends AppCompatActivity {
    private static final String TAG = "infected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infected);
        //제목줄 제목 글씨를 닉네임으로
//        getSupportActionBar().setTitle("확진자 데이터");
        //main activity에서 id값 받아오기
        Intent intent = getIntent();
        String userId = intent.getStringExtra("user id");
        Log.d("TAG", "get userid from loginActivity : "+ userId);
        Toast.makeText(InfectedActivity.this, "id : "+userId,Toast.LENGTH_LONG).show();


    }
}