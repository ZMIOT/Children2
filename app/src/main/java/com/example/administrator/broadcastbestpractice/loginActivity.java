package com.example.administrator.broadcastbestpractice;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends BaseActivity {

    private SharedPreferences pref;
    // 登陆按钮
    private TextView logbtn;
    // 调试文本，注册文本
    private TextView regtv;
    // 显示用户名和密码
    EditText username, password;
    // 返回的数据
    private String info;
    // 返回主线程更新数据
    private CheckBox rememberPass;
    private SharedPreferences.Editor editor;
    private static Handler handler = new Handler();
    private MyDatabasehelper dbhelper=new MyDatabasehelper(this,"User.db",null,3);

    //广播对象
    private MyBroadReceiver mbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psd);
        logbtn = (TextView) findViewById(R.id.login);
        regtv = (TextView) findViewById(R.id.register);
        CheckBox rememberPass=(CheckBox)findViewById(R.id.cb);

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String pass=pref.getString("password","");
            username.setText(account);
            password.setText(pass);
            rememberPass.setChecked(true);
        }
        // 设置按钮监听器
        /**
         * 登录的点击事件
         */
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建子线程，分别进行Get和Post传输
                new Thread(new MyThread()).start();

            }
        });

        regtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regItn = new Intent(loginActivity.this, RegisterActivity.class);
                // overridePendingTransition(anim_enter);
                startActivity(regItn);

            }
        });


     /*   *//**
         * 登录时动态注册广播
         *//*
        mbr=new MyBroadReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("myAction");
        loginActivity.this.registerReceiver(mbr,filter);*/
    }

    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    /**
                     * json解析
                     */
                    try {
                        SQLiteDatabase db=dbhelper.getWritableDatabase();
                       /* if(info!=null&&!"".equals(info)){*/
                            JSONArray jsonArray =new JSONArray(info);
                            if(info.equals("failed"))
                            {
                                Toast.makeText(loginActivity.this,"请注册",Toast.LENGTH_SHORT).show();
                            }
                            else{
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject != null) {
                                    SharedPreferences.Editor editor1 = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    String user = jsonObject.optString("username");
                                    String psd = jsonObject.optString("password");
                                    int st=jsonObject.optInt("state");
                                    int userflag=jsonObject.optInt("userflag");
                                    editor1.putInt("userflag",userflag);
                                    editor1.apply();
                                    Log.i("state",""+st);
                                    Log.i("user",user);
                                    Log.i("password",psd);
                                    if (username.getText().toString().equals(user) && password.getText().toString().equals(psd)) {
                                        Log.i("dddd", "shdiofsokgfkdsjg");
                                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                        editor.putString("username", username.getText().toString());
                                        editor.putString("password", password.getText().toString());
                                        editor.apply();
                                        ContentValues values=new ContentValues();
                                        values.put("username",user);
                                        values.put("password",psd);
                                        if(!db.rawQuery("select * from User where username=?",new String[]{user}).moveToFirst())
                                        {
                                            db.insert("User",null,values);
                                        }
                                        Intent intent1 = new Intent(loginActivity.this, MainActivity.class);
                                        intent1.putExtra("state",st);
                                        intent1.putExtra("userflag",userflag);
                                        startActivity(intent1);

                                    }
                                    else
                                    {
                                        Toast.makeText(loginActivity.this,"用户名或密码不正确，请重新填写",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}
