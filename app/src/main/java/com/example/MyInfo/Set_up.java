package com.example.MyInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.broadcastbestpractice.ActivityCollector;
import com.example.administrator.broadcastbestpractice.BaseActivity;
import com.example.administrator.broadcastbestpractice.R;

import java.io.BufferedWriter;
import java.util.HashMap;

import utils.HttpUtil;

public class Set_up extends BaseActivity {
    private String info;
    private SharedPreferences pref;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/RegLet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        Button exit=(Button)findViewById(R.id.exit);
        Button unbind=(Button)findViewById(R.id.unbind);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ActivityCollector.finishAll();
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new adminThread()).start();
            }
        });

    }
    private class adminThread implements Runnable{
        Message msg = new Message();
        public HashMap<String,String> getParams() {
            pref=getSharedPreferences("data",MODE_PRIVATE);
            String username=pref.getString("username","");
            HashMap<String,String> params = new HashMap<>();
            params.put("state","state");
            params.put("username",username);
            return params;
        }
        @Override
        public void run() {

            info = HttpUtil.post(KGURL, getParams());
            msg.obj = info;
            uHandler.sendMessage(msg);
        }
    }
    private Handler uHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch((String)msg.obj)
            {
                case "state success":
                    Toast.makeText(Set_up.this,"解绑成功",Toast.LENGTH_SHORT).show();
                    break;
                case "state filed":
                    Toast.makeText(Set_up.this,"解绑失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
