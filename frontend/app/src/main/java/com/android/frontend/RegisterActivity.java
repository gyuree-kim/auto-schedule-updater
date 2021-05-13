package com.android.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.frontend.Retrofit.IMyService;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_id, et_register_name, et_register_pw;
    private Button btn_register;
    //서버1
    CompositeDisposable compositeDisposable = new CompositeDisposable(); //원본엔 login activity에만 있음.
    IMyService iMyService;
    //서버 방법2
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.30.1.34:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//서버방법2
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
    /*
    private void registerUser(String name, String id, String password) {

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Name cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(id)){
            Toast.makeText(this, "Id cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(iMyService.registerUser(id, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String respond) throws Exception {
                        Toast.makeText(RegisterActivity.this, ""+respond, Toast.LENGTH_SHORT).show();
                    }
                }));
    }*/
    private void registerUser(String name, String id, String password) {
        HashMap<String, String> map = new HashMap<>();

        map.put("name", name);
        map.put("id", id);
        map.put("password", password);

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