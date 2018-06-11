package com.example.GradeCircle;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.MyInfo.Update_password;
import com.example.administrator.broadcastbestpractice.R;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import utils.HttpUtil;

public class Comment_add extends AppCompatActivity {

    private EditText commentTitle;
    private EditText commentUrl;
    private Button save;
    private SharedPreferences pref;
    private EditText babyname;
    private String info=null;
    private Handler addHandler;
    private String KGURL="http://192.168.43.143:8081/HelloWeb/CommentLet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_add);
         commentTitle=(EditText)findViewById(R.id.comment_title_add);
         commentUrl=(EditText)findViewById(R.id.comment_url_add);
         save=(Button)findViewById(R.id.comment_save_add);
        babyname=(EditText)findViewById(R.id.comment_babyname);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new commentAddThread()).start();
            }
        });

        addHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    switch ((String)msg.obj)
                    {
                        case "comment insert success":
                            Toast.makeText(Comment_add.this,"评语添加成功",Toast.LENGTH_SHORT).show();
                            break;
                        case "":
                            Toast.makeText(Comment_add.this,"评语添加失败",Toast.LENGTH_SHORT).show();
                            break;
                            default:
                                break;
                    }
            }
        };

    }
    public HashMap<String, String> getParams() {
        pref=getSharedPreferences("data",MODE_PRIVATE);
        String user=pref.getString("username","");
        String urlStr=null;
        String title=commentTitle.getText().toString();
        String bn=babyname.getText().toString();
        urlStr =commentUrl.getText().toString();
        SimpleDateFormat formatter=new  SimpleDateFormat ("yyyy-MM-dd  HH:mm:ss");
        Date  curDate= new Date(System.currentTimeMillis());//获取当前时间
        String timestr= formatter.format(curDate);
        HashMap<String, String> params = new HashMap<>();
        pref=getSharedPreferences("data",MODE_PRIVATE);
        String username=pref.getString("username","");
        params.put("comment","insert");
        params.put("title",title);
        params.put("url",urlStr);
        params.put("babyname",bn);
        params.put("time",timestr);
        params.put("username",user);
        return params;
    }
    private class commentAddThread implements Runnable{
        Message msg=new Message();
        @Override
        public void run() {
            info = HttpUtil.post(KGURL, getParams());
            msg.obj=info;
            addHandler.sendMessage(msg);
        }
    }
}
