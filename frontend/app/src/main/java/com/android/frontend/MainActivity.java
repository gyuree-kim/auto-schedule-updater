package com.android.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RoomsFragment frooms;
    private ScheduleFragment fschedule;
    private SettingFragment fsetting;
    //server
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.30.1.57:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //서버
        //server retrofit 과 연결
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        //로그인 정보
        retrofitInterface.executeUser("aaa").enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
//                if(response.isSuccessful()){
//                    List<UserItem> data = response.body();
//                    Log.d("main","successget");
//                    Log.d("main", data.get(0).getName());
//                    Toast.makeText(MainActivity.this, "getuser"+data.get(0).getName(),
//                            Toast.LENGTH_LONG).show();
                    if(response.code() == 200){
                        UserItem user = response.body();
                        Toast.makeText(MainActivity.this, "getuser"+user.getName(),Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "user find success",
                                Toast.LENGTH_LONG).show();
                        Log.d("main", String.valueOf(response.code()));
                    } else if (response.code() == 404) {
                        Toast.makeText(MainActivity.this, "user not found",
                                Toast.LENGTH_LONG).show();
                        Log.d("main", String.valueOf(response.code()));
                    }
                    else if (response.code() == 500) {
                        Toast.makeText(MainActivity.this, "db failure",
                                Toast.LENGTH_LONG).show();
                        Log.d("main", String.valueOf(response.code()));
                    }
//                }
            }
            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail response",
                        Toast.LENGTH_LONG).show();
                Log.d("main","response fail");
                t.printStackTrace();
            }
        });
        
        bottomNavigationView = findViewById(R.id.bottom_navi);

        frooms = new RoomsFragment();
        fschedule = new ScheduleFragment();
        fsetting = new SettingFragment();
        //setFrag(0); //첫번째 fragment는 rooms화면
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.main_frame, frooms);
        ft.commit();




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.roomsFragment:
                        setFrag(0);
                        break;
                    case R.id.scheduleFragment:
                        setFrag(1);
                        break;
                    case R.id.settingFragment:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

    }
//fragment 교체하는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction(); //실제적인 frag교체
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frooms);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, fschedule);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, fsetting);
                ft.commit();
                break;

        }
    }


}