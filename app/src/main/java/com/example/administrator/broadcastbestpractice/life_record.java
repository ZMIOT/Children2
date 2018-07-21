package com.example.administrator.broadcastbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/4/4.
 */

public class life_record extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* return super.onCreateView(inflater, container, savedInstanceState);*/
        View view = inflater.inflate(R.layout.remember_day, container, false);
        return view;
    }
}
