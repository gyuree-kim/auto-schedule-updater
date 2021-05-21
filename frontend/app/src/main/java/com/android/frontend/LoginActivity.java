package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class LoginActivity extends AppCompatActivity {

    private EditText et_login_id, et_login_pw;
    private Button btn_login, btn_login_register;

    //server
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://59.16.214.224:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //server
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        //init view
        et_login_id = (EditText) findViewById(R.id.et_id);
        et_login_pw = (EditText) findViewById(R.id.et_pw);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login_register = (Button) findViewById(R.id.btn_login_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(et_login_id.getText().toString(), et_login_pw.getText().toString());
                //성공했을때만 다음화면으로 넘어감
                LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("object", user);
                LoginActivity.this.startActivity(intent);
            }
        });
        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 생략함. 기능보고 추가 42:44
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent_register);
            }
        });
    }

    private void loginUser(String id, String password) {
        HashMap<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("password", password);

        Call<LoginResult> call = retrofitInterface.executeLogin(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.code() == 200) {

//                    LoginResult result = response.body();
//
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
//                    builder1.setTitle(result.getPassword());
//                    builder1.setMessage(result.getId());
//                    builder1.show();
                    //성공했을때만 다음화면으로 넘어감
                    //Intent intent_list = new Intent(LoginActivity.this, EmptyActivity.class);
                    //LoginActivity.this.startActivity(intent_list);

                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}