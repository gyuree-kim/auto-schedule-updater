package com.android.frontend.sns;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.frontend.R;


public class SettingFragment extends Fragment {
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        // Inflate the layout for this fragment
        Toast.makeText(this.getContext(), "This is settingFragment", Toast.LENGTH_SHORT).show();

        return view;//inflater.inflate(R.layout.fragment_setting, container, false);
    }
}