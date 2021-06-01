package com.android.frontend.sns;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.frontend.R;
import com.android.frontend.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddroomDialog {
    private Context context;
    private Button btn_add1;
    private Button btn_add2;
    private String userId;

    public AddroomDialog(Context context){
        this.context = context;
    }
    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String userId) {

        userId = userId;
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.content_search);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        btn_add1 = (Button) dlg.findViewById(R.id.btn_create_room1);
        btn_add2 = (Button) dlg.findViewById(R.id.btn_create_room2);

        btn_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    //createRoom
                creatRoom();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

    }
    private void creatRoom(){
        //server와 연결
        RetrofitClient retrofitClient = new RetrofitClient();
        //전달값을 map에 저장.
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("invitedUsers", "b");

//        List list = new ArrayList<>();
//        list.add("b");
//        list.add("user");
//        map.put("invitedUsers", list); //임의로 bbn을 초대 이거 형식 map인지 다시 물어볼것
        //excute login으로 post
//        Call<Void> call = retrofitClient.server.createRoom(map);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Log.d("addroom", String.valueOf(response.code()));
//                if (response.code() == 201) {
//                    //Toast.makeText(AddroomDialog.this, "Login successfully", Toast.LENGTH_LONG).show();
//                    Log.d("addroom", String.valueOf(response.code()));
//
//                } else if (response.code() == 404) {
//                    //Toast.makeText(LoginActivity.this, "Wrong Credentials",Toast.LENGTH_LONG).show();
//                    Log.d("addroom", String.valueOf(response.code()));
//                } else if (response.code() == 500) {
//                    //Toast.makeText(LoginActivity.this, "Wrong Credentials",Toast.LENGTH_LONG).show();
//                    Log.d("addroom", String.valueOf(response.code()));
//                }
//            }
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.d("addroom","response fail");
//            }
//        });
    }
}
