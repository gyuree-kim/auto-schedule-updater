package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

<<<<<<< HEAD
=======
import static java.sql.DriverManager.println;

>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_id, et_register_name, et_register_pw;
    private Button btn_register;

    //서버
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
        setContentView(R.layout.activity_register);
//서버
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


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
        HashMap<String, String> map = new HashMap<>();

        map.put("name", name);
        map.put("id", id);
        map.put("password", password);

<<<<<<< HEAD
        Call<Void> call = retrofitInterface.executeSignup(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this,
                            "Signed up successfully", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    Toast.makeText(RegisterActivity.this,
                            "Already registered", Toast.LENGTH_LONG).show();
=======
        Call<Void> call = retrofitInterface.executeRegister(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("register", String.valueOf(response.code()));

                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this,
                            "Signed up successfully", Toast.LENGTH_LONG).show();
                    Log.d("register", String.valueOf(response.code()));

                } else if (response.code() == 400) {
                    Toast.makeText(RegisterActivity.this,
                            "Already registered", Toast.LENGTH_LONG).show();
                    Log.d("register", String.valueOf(response.code()));
>>>>>>> 4bd3a15f27cd8e7eaa7735909bab81ee9673d36e
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