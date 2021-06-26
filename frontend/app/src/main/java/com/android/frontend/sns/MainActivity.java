package com.android.frontend.sns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.frontend.R;
import com.android.frontend.RetrofitClient;
import com.android.frontend.RetrofitInterface;
import com.android.frontend.UserItem;
import com.android.frontend.sns.appointments.ScheduleFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private String userId;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RoomsFragment fnotice;
    private ScheduleFragment finfected;
    private SettingFragment fsetting;
    //server
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.30.1.57:3000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //main activity에서 id값 받아오기
        Intent intent = getIntent();
        userId = intent.getStringExtra("user id");
        Log.d("main", "get userid from loginActivity"+ userId);
        if(userId.equals("")){
            Toast.makeText(MainActivity.this, "입력한 아이디가 없다.",Toast.LENGTH_LONG).show();
        }


        getLoginUser(userId);
        //로그인 유저 정보 받아오기


        initFrag();
        //fragment 세팅 초기화하기



    }

    private void getLoginUser(String userid){
        //server retrofit 과 연결
        RetrofitClient retrofitClient = new RetrofitClient();

        retrofitClient.server.getUserById(userid).enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                //if (response.isSuccessfule()){
                if(response.code() == 200){
                    UserItem user = response.body();
                    Toast.makeText(MainActivity.this, "getuser : "+user.getName(),Toast.LENGTH_LONG).show();
                    Log.d("main", String.valueOf("getuser : "+user.getName() + response.code()));
                } else if (response.code() == 404) {
//                    Toast.makeText(MainActivity.this, "user not found",
//                            Toast.LENGTH_LONG).show();
                    Log.d("main", String.valueOf("user not found" + response.code()));
                }
                else if (response.code() == 500) {
//                    Toast.makeText(MainActivity.this, "db failure",
//                            Toast.LENGTH_LONG).show();
                    Log.d("main", "db failure"+String.valueOf(response.code()));
                }
//                }
            }
            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "fail response",
//                        Toast.LENGTH_LONG).show();
                Log.d("main","response fail"+t.toString());
                t.printStackTrace();
            }
        });
    }

    private void initFrag(){
        bottomNavigationView = findViewById(R.id.bottom_navi);

        fnotice = new RoomsFragment();
        finfected = new ScheduleFragment();
        fsetting = new SettingFragment();
        //activity에서 frag으로 값 전달
        Bundle bundle = new Bundle(1);  //파라미터는 전달할 값의 개수
        bundle.putString("userId", userId); //키와 값
        fnotice.setArguments(bundle);

//       setFrag(0); //첫번째 fragment는 rooms화면
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.main_frame, fnotice);
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
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, fnotice);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, finfected);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, fsetting);
                ft.commit();
                break;

        }
    }


}