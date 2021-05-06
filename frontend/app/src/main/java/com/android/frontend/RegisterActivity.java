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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_id, et_register_name, et_register_pw;
    private Button btn_register;
    CompositeDisposable compositeDisposable = new CompositeDisposable(); //원본엔 login activity에만 있음.
    IMyService iMyService;

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
                Intent intent_login = new Intent(RegisterActivity.this, HomeActivity.class);
                RegisterActivity.this.startActivity(intent_login);
            }
        });

    }
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
    }
}