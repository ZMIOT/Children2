package com.example.administrator.broadcastbestpractice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    private String info;

    private TextView reg1;
    public  TextView user=null,psd=null;
    private SharedPreferences pref;
    private int userflag;
    private View view;
    private EditText checkcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView register = (TextView) findViewById(R.id.buttonregister);//点击注册的按钮
        user= (EditText) findViewById(R.id.user);//user文本
        psd = (EditText) findViewById(R.id.password1);
        final EditText Recheck = (EditText) findViewById(R.id.recheck);
        final TextView login = (TextView) findViewById(R.id.login);
        final RadioButton parent=(RadioButton)findViewById(R.id.parent);
        final RadioButton teacher=(RadioButton)findViewById(R.id.teacher);
        final RadioButton admin=(RadioButton)findViewById(R.id.administor);
        checkcode=(EditText)findViewById(R.id.checkcode);
        final LinearLayout tt=(LinearLayout)findViewById(R.id.regiter_checkcode_wrapper);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                if(radioButton.getId()==teacher.getId())
                {
                     userflag=1;
                   tt.setVisibility(View.VISIBLE);
                }
                if(radioButton.getId()==parent.getId())
                {
                    userflag=0;
                    tt.setVisibility(View.GONE);
                }
                if(radioButton.getId()==admin.getId())
                {
                     userflag=2;
                    tt.setVisibility(View.VISIBLE);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String password = psd.getText().toString();
                String recheck = Recheck.getText().toString();
                /**
                 * 添加数据
                 */
                if (password.equals(recheck) && !password.isEmpty() && !username.isEmpty()) {
                    new Thread(new MyThread()).start();
                }
                else if(!checkNetwork()){
                    Toast.makeText(RegisterActivity.this,"请您连接网络",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"账号不正确或者密码不一致",Toast.LENGTH_SHORT).show();
                }

            }

        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            String code=checkcode.getText().toString();
            if(code.equals("CCC")){
                info = WebService.executeHttpGetForReg(user.getText().toString(), psd.getText().toString(),userflag,code);
            }
            else{
                code=null;
                info = WebService.executeHttpGetForReg(user.getText().toString(), psd.getText().toString(),userflag,code);
            }
            Message message = new Message();
            message.obj=info;
            Log.i("reg",info);
            handler.sendMessage(message);

           /* if(info.equals("success")){
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            }
            else if(info.equals("该账号已经注册过了")){
                Toast.makeText(RegisterActivity.this,"账号已经注册过了",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(RegisterActivity.this,"服务器出错",Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

                if(msg.equals("success")){
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
                else if(info.equals("该账号已经注册过了")){
                    Toast.makeText(RegisterActivity.this,"账号已经注册过了",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }

            }

    };


}
