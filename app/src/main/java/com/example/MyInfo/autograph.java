package com.example.MyInfo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;
import com.example.administrator.broadcastbestpractice.loginActivity;
import com.example.utils.WebService;
import com.sun.jna.platform.win32.WinNT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Baseclass.Info;


public class autograph extends AppCompatActivity {
    private SharedPreferences pref;
    private ImageView backToMyInfo;
    private TextView save;
    private EditText edit_autograph;
    public MyDatabasehelper dbhelper;
    public String info;
    private static Handler handler = new Handler();
    private String user;
    private String autograph;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autograph);
         backToMyInfo=(ImageView)findViewById(R.id.backToMyInfo_autograph);
         save=(TextView)findViewById(R.id.save_autograph);
         edit_autograph=(EditText)findViewById(R.id.edit_autogarph);
        dbhelper=new MyDatabasehelper(this,"User.db",null,3);

        backToMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(autograph.this, Personal_info.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref=getSharedPreferences("data",MODE_PRIVATE);//获取到当前对象
                user=pref.getString("username","");
                autograph=edit_autograph.getText().toString();
                SQLiteDatabase db=dbhelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("autograph",autograph);
                db.update("User",values,"username=?",new String[]{user});
                editor=getSharedPreferences("info",MODE_PRIVATE).edit();
                editor.putString("autograph",autograph);
                Intent intent=new Intent(autograph.this, Personal_info.class);
                startActivity(intent);
            }
        });
    }


}
