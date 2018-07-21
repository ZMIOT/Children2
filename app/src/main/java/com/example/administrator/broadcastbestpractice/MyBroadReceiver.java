package com.example.administrator.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyBroadReceiver extends BroadcastReceiver {
    setValues sv;

    @Override
    public void onReceive(Context context, Intent intent) {
        int userflag = intent.getIntExtra("userflag", 0);
        Log.i("mybroadreceiver", "" + userflag);
        sv.ufg(userflag);
    }

    public interface setValues {
        void ufg(int content);
    }

    public void getValues(setValues sv) {
        this.sv = sv;
    }
}
