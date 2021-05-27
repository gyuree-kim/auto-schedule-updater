package com.android.frontend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
//roomlist 출력테스트용. 나중에 삭제

public class UserAdapter extends BaseAdapter {
    ArrayList<UserItem> users = new ArrayList<>();
    @Override
    public int getCount() { //총 몇개인지
        return users.size();
    }

    @Override
    public Object getItem(int position) {   //데이터 보내기
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {   //어디에있는지 찾기
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //뷰홀더
        ViewHolder holder;

        //보는 뷰 , 리스트뷰 묶은거
        Context c = parent.getContext();    //고유한 값을 준다.
        if(convertView == null){
            LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.layout_chatroom, parent,false);  //false바로 붙일것 아님. 하나하나 붙일것.
//            holder = new ViewHolder();  //뷰홀더더
//            holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_last_message);
//            holder.tv_read = (TextView) convertView.findViewById(R.id.tv_last_notread);
//            holder.tv_sender = (TextView) convertView.findViewById(R.id.tv_last_sender);
//            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_last_time);
//            convertView.setTag(holder);
            Log.d("rooms", "어뎁터 널");

        } else{
//            holder = (ViewHolder)convertView.getTag();
            Log.d("rooms", "어뎁터 엘스");


        }
        TextView tv_msg = (TextView) convertView.findViewById(R.id.tv_last_message);
        TextView tv_read = (TextView) convertView.findViewById(R.id.tv_last_notread);
        TextView tv_sender = (TextView) convertView.findViewById(R.id.tv_last_sender);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_last_time);
        //ImageView iv_profile = convertView.findViewById(R.id.imgv_profile);
        UserItem u = users.get(position);   //총 몇개의 배열이 있는지.

//        holder.tv_msg.setText(u.getId());
//        holder.tv_read.setText(String.valueOf(1));
//        holder.tv_sender.setText(u.getName());
//        holder.tv_time.setText(u.getPassword());
        tv_msg.setText(u.getId());
        tv_read.setText(String.valueOf(1));
        tv_sender.setText(u.getName());
        tv_time.setText(u.getPassword());
//        iv_profile.setImageDrawable(r.getProfile()); 아직 함수안만듬


        return convertView;
    }

    private class ViewHolder {
        protected TextView tv_msg, tv_read, tv_sender, tv_time;

    }
    public void addUserItem(UserItem u){

        users.add(u);
    }
    public void addUser(String last_message, int last_notread, String last_sender, String last_time){
        UserItem u = new UserItem();
        u.setId(last_message);
        u.setName(last_sender);
        u.setPassword(last_time);
        users.add(u);
    }
}
