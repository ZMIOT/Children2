package com.example.MyInfo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
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
import com.example.utils.WebService;

import Baseclass.Info;

public class myName extends AppCompatActivity {
    public MyDatabasehelper dbhelper;
    private SharedPreferences pref;
    private String nick;
    private String info;
    private String user;
    private String nickname;
    public SharedPreferences.Editor editor;
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_name);
        ImageView backTomyInfo = (ImageView) findViewById(R.id.backToMyInfo);
        TextView save = (TextView) findViewById(R.id.save);
        final EditText edit_myName = (EditText) findViewById(R.id.edit_myName);
        dbhelper = new MyDatabasehelper(this, "User.db", null, 3);

        backTomyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(myName.this,Personal_info.class);
                startActivity(intent);*/
                Back();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getSharedPreferences("data", MODE_PRIVATE);//获取到当前对象
                user = pref.getString("username", "");
                nickname = edit_myName.getText().toString();
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nickname", nickname);
                db.update("User", values, "username=?", new String[]{user});
                editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("nickname", nickname);
                Intent intent = new Intent(myName.this, Personal_info.class);
                startActivity(intent);
            }
        });
    }





                       /* if(info.equals("insertsuccess"))
                        {
                            Log.i("insert auto","success");
                            SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putString("nickname",nick);
                            editor.apply();
                            Intent intent=new Intent(myName.this,MainActivity.class);
                            startActivity(intent);
                        }
                        *//*}*/


    public void Back() {
        Intent intent = new Intent(myName.this, Personal_info.class);
        startActivity(intent);
    }
}
