package com.android.frontend.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;

import com.android.frontend.infected.InfectedActivity;
import com.android.frontend.R;
import com.android.frontend.RetrofitClient;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;


public class LoginActivity extends AppCompatActivity implements AutoPermissionsListener{


    private EditText et_login_id, et_login_pw;
    private Button btn_login, btn_login_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //필요한 위험 sms 권한 다 띄움
        AutoPermissions.Companion.loadAllPermissions(this, 101);


        //init view
        et_login_id = (EditText) findViewById(R.id.et_id);
        et_login_pw = (EditText) findViewById(R.id.et_pw);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login_register = (Button) findViewById(R.id.btn_login_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(et_login_id.getText().toString(), et_login_pw.getText().toString());

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
    //위험권한을 사용자가 승인했는지 나옴
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions,this);
    }
    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this,"login] 권한거부됨"+strings.length,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this,"login] 권한승인됨"+strings.length,Toast.LENGTH_LONG).show();
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


                    Toast.makeText(LoginActivity.this, "login] Login successfully", Toast.LENGTH_LONG).show();
                    Log.d("login", String.valueOf(response.code()));
                    //성공했을때만 다음화면으로 넘어감
                    //LoginResult user = new LoginResult(et_login_id.getText().toString(), et_login_pw.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), InfectedActivity.class);

                    intent.putExtra("userId", id); //id값 넘겨줌

                    LoginActivity.this.startActivity(intent);

                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "login] Wrong Credentials",
                            Toast.LENGTH_LONG).show();
                    Log.d("login", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "login] respond fail "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("login","response fail");
            }
        });

    }
}