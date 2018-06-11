package com.example.GradeCircle;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.MyInfo.Personal_info;
import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;
import com.example.administrator.broadcastbestpractice.loginActivity;
import com.example.utils.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import FragmentTest.FragmentPage2;
import utils.HttpUtil;

public class add_to_nusery extends AppCompatActivity {

    private MyDatabasehelper dbhelper;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private  int state=0;
    private String admin;
    private String info;
    private String st=null;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/BabyLet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_nusery);
        Button ensure=(Button)findViewById(R.id.ensure);
        final EditText teacherNumber=(EditText)findViewById(R.id.teacherNumber);
        ImageView backtoFrament1=(ImageView)findViewById(R.id.backToFrament1);
        dbhelper=new MyDatabasehelper(this,"User.db",null,3);
        ensure.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (teacherNumber.getText().toString().equals("15755080823")) {
                      Intent intent=getIntent();
                      final String babyname=intent.getStringExtra("babyname");
                      final String babysex=intent.getStringExtra("babysex");
                      final String babybirth=intent.getStringExtra("babybirth");
                      final String relationship=intent.getStringExtra("relationship");
                      final String parentphone=intent.getStringExtra("parentphone");
                      final String babyclass=intent.getStringExtra("babyclass");
                      final String babysummary=intent.getStringExtra("babysummary");
                      new Thread(new Runnable() {
                          final HashMap<String, String> params = new HashMap<>();
                          public HashMap<String, String> getParams() {
                              params.put("baby","insert");
                              params.put("babyname",babyname);
                              params.put("babybirth",babybirth);
                              params.put("babysex",babysex);
                              params.put("relationship",relationship);
                              params.put("parentphone",parentphone);
                              params.put("babyclass",babyclass);
                              params.put("babysummary",babysummary);
                              return params;
                          }
                          @Override
                          public void run() {
                              state=1;
                              pref = getSharedPreferences("data", MODE_PRIVATE);
                                  admin = pref.getString("username", "");
                                  st = WebService.executeHttpGetChangeState(admin, state);
                                  info= HttpUtil.post(KGURL,getParams());
                                  pref = getSharedPreferences("data", MODE_PRIVATE);
                                  String user = pref.getString("username", "");
                                  SQLiteDatabase db = dbhelper.getWritableDatabase();
                                  ContentValues values = new ContentValues();
                                  values.put("state", 1);
                                  db.update("User", values, "username=?", new String[]{user});
                                  editor = getSharedPreferences("state", MODE_PRIVATE).edit();
                                  editor.putInt("state", 1);
                                  editor.apply();
                                  Intent i = new Intent();
                                  i.setClass(add_to_nusery.this, MainActivity.class);
                                  //一定要指定是第几个pager，因为要跳到ThreeFragment，这里填写2
                                  i.putExtra("id", 2);
                                  startActivity(i);
                              Message message = new Message();
                              message.obj = info;
                              handler.sendMessage(message);
                          }
                      }).start();
                  }
                  else
                  {
                      Toast.makeText(add_to_nusery.this, "請輸入正確的邀請碼", Toast.LENGTH_SHORT).show();
                  }

              }
        });

        backtoFrament1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                switch ((String)msg.obj){
                    case "baby info insert failed":
                        Toast.makeText(getApplicationContext(), "哎哟，绑定失败！", Toast.LENGTH_SHORT).show();
                        break;
                    case "baby info insert success":
                        Toast.makeText(getApplicationContext(), "绑定成功！", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(add_to_nusery.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
        }
    };
}
