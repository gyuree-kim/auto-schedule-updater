package com.android.frontend.sns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.frontend.R;

import java.util.ArrayList;

public class RoomAdapter extends BaseAdapter {
    ArrayList<RoomItem> rooms = new ArrayList<>();
    @Override
    public int getCount() { //총 몇개인지
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {   //데이터 보내기
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {   //어디에있는지 찾기
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //보는 뷰 , 리스트뷰 묶은거
        Context c = parent.getContext();    //고유한 값을 준다.
        if(convertView == null){
            LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.layout_chatroom, parent,false);  //false바로 붙일것 아님. 하나하나 붙일것.

        }
        TextView tv_msg = convertView.findViewById(R.id.tv_last_message);
        TextView tv_read = convertView.findViewById(R.id.tv_last_notread);
        TextView tv_sender = convertView.findViewById(R.id.tv_last_sender);
        TextView tv_time = convertView.findViewById(R.id.tv_last_time);
        //ImageView iv_profile = convertView.findViewById(R.id.imgv_profile);
        RoomItem r = rooms.get(position);   //총 몇개의 배열이 있는지.

        tv_msg.setText(r.getLast_message());
        tv_read.setText(String.valueOf(r.getLast_notread()));
        tv_sender.setText(r.getLast_sender());
        tv_time.setText(r.getLast_time());
        //iv_profile.setImageDrawable(r.getProfile()); 아직 함수안만듬


        return convertView;
    }
    public void addRoom(String last_message, int last_notread, String last_sender, String last_time){
        RoomItem r = new RoomItem();
        r.setLast_message(last_message);
        r.setLast_notread(last_notread);
        r.setLast_sender(last_sender);
        r.setLast_time(last_time);
        rooms.add(r);
    }
    public void addRoomItem(RoomItem r){
        rooms.add(r);
    }
}
