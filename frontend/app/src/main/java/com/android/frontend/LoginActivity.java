package com.android.frontend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.frontend.Retrofit.IMyService;
import com.android.frontend.Retrofit.RetrofitClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText et_login_id, et_login_pw;
    private Button btn_login, btn_login_register;
//서버 방법1
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
//서버 방법2
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.30.1.34:3000";


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //서버방법2
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

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
                //Intent intent_list = new Intent(LoginActivity.this, EmptyActivity.class);
                //LoginActivity.this.startActivity(intent_list);
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
    /*
    private void loginUser(String id, String password) {
        if(TextUtils.isEmpty(id)){
            Toast.makeText(this, "Name cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(iMyService.loginUser(id, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }));
    }*/
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
//
//                    builder1.show();

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