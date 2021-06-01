package com.android.frontend.sns.appointments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import com.android.frontend.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleFragment extends Fragment {
    Context context;
    private View view;

    private String userId;
    private Date time;
    private String location;

    private ArrayList<Appointments> AppointmentList;
    private ListView listView;
    private ListViewAdapter adapter;

    private Retrofit retrofit;
    private AppointmentRetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.30.1.48:3000";

    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // scheduleFragment에 대한 context, view 받아오기
        context = getContext();
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // MainActivity에서 유저 정보 받아오기
//        userId = ((MainActivity) getActivity()).getUserId();
//        time = ((MainActivity) getActivity()).getTime();
//        location = ((MainActivity) getActivity()).getLocation();

        userId = "aaa";
        location = "한양대역";

        // appointments 담을 listview, list 선언
        listView = (ListView) view.findViewById(R.id.appointmentsListView);
        ArrayList<Appointments> appointmentList = new ArrayList<Appointments>();

        // retrofit으로 서버 연결
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(AppointmentRetrofitInterface.class);

        // 목록 갱신
        initAppointments();

        // add button click listener
//        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                handleAddDialog();
//            }
//        });

        // list item click listener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(MainActivity, AppointmentList)
//
//            }
//        });

        // 기타 view 컴포넌트별로 처리
        // ...

        return view;

    }

    private void initAppointments(){
        Call<AppointmentResponse> call = retrofitInterface.executeInit(userId);

        call.enqueue(new Callback<AppointmentResponse>() {
            @Override
            // server로부터 응답 받으면
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                System.out.println("appointment init!");
                // 정상적으로 response 받으면 appointment 갱신
                if(response.code() == 200){
                    if (response.body() != null) {
                        ArrayList<Appointments> list = response.body().getAppointments();

                        ArrayList<Appointments> arraylist = new ArrayList<Appointments>();
                        arraylist.addAll(list);
                        adapter = new ListViewAdapter(list, context);

                        listView.setAdapter(adapter);
                    }
                }
                // userId에 해당하는 유저가 없을경우
                else if(response.code()==404){
                    Toast.makeText(context, "User not found",
                            Toast.LENGTH_LONG).show();
                }
                // server error
                else if(response.code()==500){
                    Toast.makeText(context, "Server eror",
                            Toast.LENGTH_LONG).show();
                }
            }

            // 에러 발생시
            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(context, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    // add 버튼 이벤트 핸들러
    private void handleAddDialog(){

    }
    // 그 외 event handling
    // ...
}