package com.android.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.frontend.appointments.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RoomsFragment frooms;
    private ScheduleFragment fschedule;
    private SettingFragment fsetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bar 바꿔보기a


        bottomNavigationView = findViewById(R.id.bottom_navi);

        frooms = new RoomsFragment();
        fschedule = new ScheduleFragment();
        fsetting = new SettingFragment();
        setFrag(0); //첫번째 fragment는 rooms화면
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