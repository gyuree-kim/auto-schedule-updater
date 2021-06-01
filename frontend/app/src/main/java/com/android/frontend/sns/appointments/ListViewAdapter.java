package com.android.frontend.sns.appointments;
import com.android.frontend.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

// listView에 연결시켜주는 adapter
public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Appointments> AppointmentList;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public ListViewAdapter(List<Appointments> list, Context context){
        this.AppointmentList = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflate.inflate(R.layout.fragment_schedule, null);

            viewHolder = new ViewHolder();

            viewHolder.chatRoomId = (TextView) convertView.findViewById(R.id.chatRoomId);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.chatRoomId.setText(AppointmentList.get(position).getChatRoomId());
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Date to String
        String stringTime = transFormat.format(AppointmentList.get(position).getTime());
        viewHolder.time.setText(stringTime);

        viewHolder.location.setText(AppointmentList.get(position).getLocation());

        return convertView;
    }

    class ViewHolder{
        public TextView chatRoomId;
        public TextView time;
        public TextView location;
    }
}
