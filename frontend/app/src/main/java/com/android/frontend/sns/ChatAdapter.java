package com.android.frontend.sns;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.frontend.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends BaseAdapter{

    ArrayList<MessageSnsItem> msgs = new ArrayList<>();


    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageSnsItem m = msgs.get(position);   //현재 보여줄 position의 데이터로 뷰 구성
        //재활용할 뷰 convertView 사용안함???
        View itemView = null;
        String myname = "a"; //내이름 얻는 방법 찾기//////////////

        //보는 뷰 , 리스트뷰 묶은거
        Context c = parent.getContext();    //고유한 값을 준다.
        if(convertView == null){
            LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = li.inflate(R.layout.layout_chatroom, parent,false);  //false바로 붙일것 아님. 하나하나 붙일것.
            Log.d("rooms", "어뎁터 널");
            //sender가 나인가?
            if(m.getSender().equals(myname)){
                itemView = li.inflate(R.layout.layout_msg, parent,false);
            }else{
                itemView = li.inflate(R.layout.layout_msg_other, parent,false);

            }
        } else{
            Log.d("rooms", "어뎁터 엘스");
        }
        TextView tv_sender = (TextView) itemView.findViewById(R.id.tv_sender);
        TextView tv_msg = (TextView) itemView.findViewById(R.id.tv_msg);
        TextView tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        //ImageView iv_profile = convertView.findViewById(R.id.iv_profile);

        tv_sender.setText(m.getSender());
        tv_msg.setText(m.getContent());
        tv_time.setText(date2String(m.getCreatedAt()));
        //        iv_profile.setImageDrawable(r.getProfile()); 아직 함수안만듬


        return itemView;
    }

    public String date2String(Date d){
        SimpleDateFormat hhmm = new SimpleDateFormat("HH:MM");
        return hhmm.format(d);
    }
    public void addMsgItem(MessageSnsItem m){

        msgs.add(m);
    }
    public void addMsg(String sender, String msg, String time){
        MessageSnsItem m = new MessageSnsItem();
        m.setSender(sender);
        m.setContent(msg);
        Date d = new Date();
        m.setCreatedAt(d);    //임의로 넣음
        msgs.add(m);
    }
}