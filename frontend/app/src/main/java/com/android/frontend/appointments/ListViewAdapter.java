package com.android.frontend.appointments;
package com.example.auto_schedule_updater.appointments;
import com.example.auto_schedule_updater.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

// listView에 연결시켜주는 adapter
public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Appointmet> AppointmetList;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public ListViewAdapter(List<Appointment> list, Context, context){
        this.AppointmetList = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);

        @Override
        public int getAppointmetListSize(){
            return AppointmetList.size()
        }

        @Override
        if(convertView==null){
            convertView = inflate.inflate(R.layout.contact_listview_layout, null);

            viewHolder = new ViewHolder();

            viewHolder.chatRoomId = (TextView) convertView.findViewById(R.id.chatRoomId);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.chatRoomId.setText(AppointmetList.get(position).getChatRoomId());
        viewHolder.time.setText(AppointmetList.get(position).setTime());
        viewHolder.location.setText(AppointmetList.get(position).setLocation());

        return convertView;
    }

    class ViewHolder{
        public TextView chatRoomId;
        public TextView time;
        public TextView location;
    }
}
}