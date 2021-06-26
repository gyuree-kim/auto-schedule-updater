package com.android.frontend.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.android.frontend.R;
import com.android.frontend.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_id, et_register_name, et_register_pw;
    private Button btn_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        et_register_id = (EditText) findViewById(R.id.et_id);
        et_register_name = (EditText) findViewById(R.id.et_name);
        et_register_pw = (EditText) findViewById(R.id.et_pw);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(et_register_name.getText().toString(),
                        et_register_id.getText().toString(),
                        et_register_pw.getText().toString());
                Intent intent_login = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent_login);
            }
        });

    }

    private void registerUser(String name, String id, String password) {
        //server와 연결

        RetrofitClient retrofitClient = new RetrofitClient();

        HashMap<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("id", id);
        map.put("name", name);

        Call<Void> call = retrofitClient.server.executeRegister(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("register", String.valueOf(response.code()));

                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this,
                            "Signed up successfully", Toast.LENGTH_LONG).show();
                    Log.d("register", String.valueOf(response.code())+ map);

                } else if (response.code() == 400) {
                    Toast.makeText(RegisterActivity.this,
                            "Already registered", Toast.LENGTH_LONG).show();
                    Log.d("register", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

}