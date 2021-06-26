package com.android.frontend.infected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.frontend.R;

import java.util.ArrayList;

public class InfectedAdapter extends BaseAdapter {
    //아이템데이터리스트
    ArrayList<InfectedItem> infectedItemList = new ArrayList<InfectedItem>();

    private static final int ITEM_VIEW_TYPE_EVENT = 0;
    private static final int ITEM_VIEW_TYPE_INFECTED = 1;
    private static final int ITEM_VIEW_TYPE_MAX = 2;

    public InfectedAdapter() {
    }

    @Override
    public int getCount() {
        return infectedItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return infectedItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            InfectedItem infectedItem = infectedItemList.get(position);

            switch (viewType) {
                case ITEM_VIEW_TYPE_EVENT:
                    convertView = inflater.inflate(R.layout.layout_event, parent, false);
                    TextView tv_location = (TextView) convertView.findViewById(R.id.tv_location);
                    TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                    TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                    tv_location.setText(infectedItem.getLocation());
                    tv_date.setText(infectedItem.getDate());
                    tv_time.setText(infectedItem.getTime());
                    break;
                case ITEM_VIEW_TYPE_INFECTED:
                    convertView = inflater.inflate(R.layout.layout_infected, parent, false);

                    TextView tv_increased = (TextView) convertView.findViewById(R.id.tv_increased);

                    //iconImageView.setImageDrawable(listViewItem.getIcon());
                    String incresed = infectedItem.getLocation() + " 신규확진자 " +infectedItem.getNum()+"명";
                    tv_increased.setText(incresed);
                    break;
            }
        }

        return convertView;
    }
    //기존 아이템 추가
    public void addInfectedItem(InfectedItem i){

        infectedItemList.add(i);
    }

    // 첫 번째 아이템 추가를 위한 함수.
    public void addItem(String location, String date, String time) {
        InfectedItem item = new InfectedItem() ;

        item.setType(ITEM_VIEW_TYPE_EVENT) ;
        item.setLocation(location);
        item.setDate(date);
        item.setTime(time);

        infectedItemList.add(item) ;
    }

    // 두 번째 아이템 추가를 위한 함수.
    public void addItem(String location, int num) {//Drawable icon,
        InfectedItem item = new InfectedItem() ;

        item.setType(ITEM_VIEW_TYPE_INFECTED) ;
//        item.setIcon(icon);
        item.setLocation(location);
        item.setNum(num);

        infectedItemList.add(item);
    }

    @Override
    public int getItemViewType(int position) {
        return infectedItemList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }
}
