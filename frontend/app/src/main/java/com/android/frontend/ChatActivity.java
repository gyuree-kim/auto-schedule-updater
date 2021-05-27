package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText et_msg;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        lv = findViewById(R.id.lv_msglist);
        btn_send = findViewById(R.id.btn_send_msg);
        et_msg = findViewById(R.id.et_msg);
    }
}