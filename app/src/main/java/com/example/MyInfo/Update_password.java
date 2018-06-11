package com.example.MyInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.GradeCircle.Comment_detail;
import com.example.GradeCircle.comment_grade;
import com.example.administrator.broadcastbestpractice.R;
import com.example.utils.WebService;

import java.util.HashMap;

import BaseAdapterClass.commentAdapter;
import utils.HttpUtil;

public class Update_password extends AppCompatActivity {
    private String info;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/RegLet";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText updatepassword;
    private EditText checkpassword;

    private String uPassword;
    private String cPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
          updatepassword=(EditText)findViewById(R.id.update_password);
          checkpassword=(EditText)findViewById(R.id.checkpassword);
        pref=getSharedPreferences("data",MODE_PRIVATE);
        String password=pref.getString("password","");
        updatepassword.setText(password);
        checkpassword.setText(password);

        Button ensure_update=(Button)findViewById(R.id.ensure_update);
        ensure_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new updateThread()).start();
            }
        });
    }


    private Handler uHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((String)msg.obj)
            {
                case "update success":
                    editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("password",uPassword);

                    Toast.makeText(Update_password.this,"密码更新成功",Toast.LENGTH_SHORT).show();
                    break;
                case "update filed":
                    Toast.makeText(Update_password.this,"密码更新失败",Toast.LENGTH_SHORT).show();
                    break;
                case "error":
                    Toast.makeText(Update_password.this,"密码不一致，请重新填写",Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        break;
            }
        }
    };


    public HashMap<String, String> getParams() {
        uPassword=updatepassword.getText().toString();
        cPassword=checkpassword.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        pref=getSharedPreferences("data",MODE_PRIVATE);
        String username=pref.getString("username","");
        params.put("update_pass","updatepassword");
        params.put("password",uPassword);
        params.put("username",username);
        return params;
    }
    private class updateThread implements Runnable{
        Message msg = new Message();
        @Override
        public void run() {
             uPassword=updatepassword.getText().toString();
             cPassword=checkpassword.getText().toString();
            if(uPassword.equals(cPassword)) {
                info = HttpUtil.post(KGURL, getParams());

                msg.obj = info;
                uHandler.sendMessage(msg);
            }
            else
            {
                msg.obj="error";
                uHandler.sendMessage(msg);
            }
        }
    }
}
