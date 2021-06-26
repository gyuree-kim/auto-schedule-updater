package com.android.frontend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.frontend.infected.InfectedActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "--------onReceive() 호출됨.");

        Bundle bundle = intent.getExtras();
        //이 번들에는 extra data인 부가데이터가 들어있다.
        //번들 파싱 메소드
        SmsMessage[] messages = parseSmsMessage(bundle);
        //표준 크로트콜 이용해서 서로 메세지를 주고 받게 되어있는데 이게 미리 객체로 만들어놓은것

        //권한 설정
        if(messages != null && messages.length>0){  //메세지 1건 이상
            String sender = messages[0].getOriginatingAddress();
            String content = messages[0].getMessageBody().toString();
            Date sentAt = new Date();
            String ssentAt = format.format(sentAt);
            //Date sentAt = new Date(messages[0].getTimestampMillis());
            String toastMsg = sender + ": " + content+ ssentAt+sentAt;
            Toast.makeText(context,toastMsg,Toast.LENGTH_LONG).show();
            Log.d(TAG, "-------내용."+toastMsg);

            sendToActivity(context, sender, content, sentAt);
        }

    }
    // 정형화된 코드. 그냥 가져다 쓰면 된다.
    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Log.d(TAG, "--------parse함수.");
        //pdus는 표준 프로토콜에 맞춰 넘어온것
        Object[] objs = (Object[])bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0;i<objs.length;i++){
            //안드로이드 버전에 따라, 23이상이면 if문
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i], format);
            }
            else{
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
            }

        }
        return messages;
    }
    private void sendToActivity(Context context, String sender, String content, Date sentAt){
        Log.d(TAG, "-------앧티비티로 전송 함수.");

        Intent intent = new Intent(context, InfectedActivity.class);
        //화면이 없는것에서 띄우는것이기에 flag해줘야함.
        //single top은 기존의 sms엑티비티 떠있으면 두번째 보낼때는 새로 만들지 않고 재사용
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("content", content);
        intent.putExtra("sentAt", sentAt);

        context.startActivity(intent);
    }
}