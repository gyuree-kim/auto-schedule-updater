package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
//                LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                //intent.putExtra("object", user);
//                LoginActivity.this.startActivity(intent);
            }
        });
        //register화면으로 넘김
        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent_register);
            }
        });
    }
    private void loginUser(String id, String password) {
        //server와 연결
        RetrofitClient retrofitClient = new RetrofitClient();
        //전달값을 map에 저장.
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
        //excute login으로 post
        Call<Void> call = retrofitClient.server.executeLogin(map);
        //call의 결과 확인
        call.enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("login", String.valueOf(response.code()));
                if (response.code() == 201) {

//                    LoginResult result = response.body();
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
//                    builder1.setTitle(result.getPassword());
//                    builder1.setMessage(result.getId());
//                    builder1.show();

                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                    Log.d("login", String.valueOf(response.code()));
                    //성공했을때만 다음화면으로 넘어감
                    //LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user id", id); //id값 넘겨줌
                    LoginActivity.this.startActivity(intent);

                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials",
                            Toast.LENGTH_LONG).show();
                    Log.d("login", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("login","response fail");
            }
        });

    }
}