package com.android.frontend;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomsFragment#} factory method to
 * create an instance of this fragment.
 * newInstance
 */
public class RoomsFragment extends Fragment {

    //인스타강의
    private View view;
    private FloatingActionButton fab_add;

//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public RoomsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment RoomsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static RoomsFragment newInstance(String param1, String param2) {
//        RoomsFragment fragment = new RoomsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        // Inflate the layout for this fragment
        ListView lv;
        //Toast.makeText(this.getContext(), "This is roomsFragment", Toast.LENGTH_SHORT).show();
        //-----------list view 채팅방 목록
        view = inflater.inflate(R.layout.fragment_rooms, container, false);
        lv = (ListView) view.findViewById(R.id.lv_chatroom_list);
        RoomList roomAdapter = new RoomList();//한번에 넣을댄 여기 인자고 this, 배열, 배열
        lv.setAdapter(roomAdapter);

        //drauable 넣을땐 ContextCompat.getDrawable(this,R.drawable.apple)
        roomAdapter.addRoom("마지막 메세지", 1, "사람", "20200521");
        roomAdapter.addRoom("최근 메세지", 2, "고양이", "20210521");
        roomAdapter.addRoom("누군가 메세지", 3, "새", "20200111");
        roomAdapter.addRoom("그냥 메세지", 1, "치타", "20200330");

        //-------추가 버튼 눌렀을때 검색창 dialog 띄우기
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.content_search);
                dialog.show();
            }
        });

        return view;//inflater.inflate(R.layout.fragment_rooms, container, false);
    }

}