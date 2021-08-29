package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.sql.DriverManager.println;


public class LoginActivity extends AppCompatActivity {

    private EditText et_login_id, et_login_pw;
    private Button btn_login, btn_login_register;

    //server
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
<<<<<<< HEAD
    private String BASE_URL = "http://59.16.214.224:3000";
=======
    private String BASE_URL = "http://172.30.1.57:3000";
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //server retrofit 과 연결
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
<<<<<<< HEAD
        println("retrofit builder() 호출됨");
=======
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
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
<<<<<<< HEAD
                LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("object", user);
                LoginActivity.this.startActivity(intent);
=======
//                LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                //intent.putExtra("object", user);
//                LoginActivity.this.startActivity(intent);
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
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
        //전달값을 map에 저장.
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
        //excute login으로 post
        Call<LoginResult> call = retrofitInterface.executeLogin(map);
        //call의 결과 확인
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
<<<<<<< HEAD
                if (response.code() == 200) {
=======
                Log.d("login", String.valueOf(response.code()));
                if (response.code() == 201) {
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e

//                    LoginResult result = response.body();
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
//                    builder1.setTitle(result.getPassword());
//                    builder1.setMessage(result.getId());
//                    builder1.show();
                    //성공했을때만 다음화면으로 넘어감
                    //Intent intent_list = new Intent(LoginActivity.this, EmptyActivity.class);
                    //LoginActivity.this.startActivity(intent_list);
<<<<<<< HEAD
=======
                    Toast.makeText(LoginActivity.this,
                            "Login successfully", Toast.LENGTH_LONG).show();
                    Log.d("login", String.valueOf(response.code()));

                    LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user id", id);
                    LoginActivity.this.startActivity(intent);
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e

                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials",
                            Toast.LENGTH_LONG).show();
<<<<<<< HEAD
=======
                    Log.d("login", String.valueOf(response.code()));
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
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