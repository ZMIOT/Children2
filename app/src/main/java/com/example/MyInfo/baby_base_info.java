package com.example.MyInfo;

import android.app.AlertDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.GradeCircle.add_to_nusery;
import com.example.GradeCircle.improve_acc_infomation;
import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Baseclass.Baby;
import utils.HttpUtil;

public class baby_base_info extends AppCompatActivity {
    private TextView show_birth;
    private LinearLayout baby_sex;
    private TextView show_sex;
    private LinearLayout baby_relationship;
    private TextView show_relationship;
    private EditText baby_name;
    private EditText parent_phone;
    private EditText baby_class;
    private EditText baby_summary;

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private String user;
    private MyDatabasehelper dbhelper;
    private Handler bhandler;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/BabyLet";
    private List<Baby> babyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_base_info);

        ImageView backtograde = (ImageView) findViewById(R.id.backToGrade);
        ImageView edit_tx_grade = (ImageView) findViewById(R.id.edit_tx_grade);
        baby_name = (EditText) findViewById(R.id.baby_name);
        LinearLayout baby_birth = (LinearLayout) findViewById(R.id.baby_birth);

        Button next_grade = (Button) findViewById(R.id.next_grade);
        baby_sex = (LinearLayout) findViewById(R.id.baby_sex);
        show_sex = (TextView) findViewById(R.id.show_sex);
        show_birth = (TextView) findViewById(R.id.show_birth);
        baby_relationship = (LinearLayout) findViewById(R.id.baby_relationship);
        show_relationship = (TextView) findViewById(R.id.show_relationship);
        parent_phone = (EditText) findViewById(R.id.parent_phone);
        baby_class = (EditText) findViewById(R.id.baby_class);
        baby_summary = (EditText) findViewById(R.id.baby_summary);

        new Thread(new baThread()).start();
        bhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch ((int) msg.obj) {
                    case 1:
                        show_sex.setText(babyList.get(0).getSex());
                        baby_name.setText(babyList.get(0).getBabyname());
                        show_birth.setText(babyList.get(0).getBirth());
                        show_relationship.setText(babyList.get(0).getRelationship());
                }
            }
        };


        baby_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showrelationsWindow();
            }
        });
        backtograde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baby_base_info.this, MainActivity.class);
                startActivity(intent);
            }
        });
        edit_tx_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        baby_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsexWindow();
            }
        });

        baby_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }
        });

        next_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!baby_name.getText().toString().isEmpty() && !show_sex.getText().toString().isEmpty() && !show_birth.getText().toString().isEmpty() && !show_relationship.getText().toString().isEmpty()) {
                    /*Intent intent = new Intent(baby_base_info.this, add_to_nusery.class);
                    intent.putExtra("babyname",baby_name.getText().toString());
                    intent.putExtra("babysex",show_sex.getText().toString());
                    intent.putExtra("babybirth",show_birth.getText().toString());
                    intent.putExtra("relationship",show_relationship.getText().toString());
                    intent.putExtra("parentphone",parent_phone.getText().toString());
                    intent.putExtra("babyclass",baby_class.getText().toString());
                    intent.putExtra("babysummary",baby_summary.getText().toString());
                    startActivity(intent);*/
                } else {
                    /* Toast.makeText(baby_base_info.this,"请将信息填写完整",Toast.LENGTH_SHORT).show();*/
                }

            }
        });
    }

    private void showrelationsWindow() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        final String ad = pref.getString("username", "");
        AlertDialog.Builder builder = new AlertDialog.Builder(baby_base_info.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(baby_base_info.this, R.layout.relations_item, null);
        final TextView man = (TextView) view.findViewById(R.id.pop_father);
        final TextView woman = (TextView) view.findViewById(R.id.pop_mather);
        //设置contentView
        man.setOnClickListener(new View.OnClickListener() {// 男
            @Override
            public void onClick(View v) {
                final ContentValues values = new ContentValues();
                values.put("sex", man.getText().toString());
                try {
                    show_relationship.setText(man.getText().toString());
                } catch (Exception e) {
                    e.getMessage();
                }
                /* cursor.close();*/
                dialog.dismiss();
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {// 女
            @Override
            public void onClick(View v) {
                try {
                    show_relationship.setText(man.getText().toString());
                } catch (Exception e) {
                    e.getMessage();
                }
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    /**
     * 性别
     */
    private void showsexWindow() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        final String ad = pref.getString("username", "");
        MyDatabasehelper dbhelper = new MyDatabasehelper(baby_base_info.this, "User.db", null, 3);
        final SQLiteDatabase db = dbhelper.getWritableDatabase();

        final Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{ad});
        AlertDialog.Builder builder = new AlertDialog.Builder(baby_base_info.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(baby_base_info.this, R.layout.sex_item, null);
        final TextView man = (TextView) view.findViewById(R.id.pop_computer);
        final TextView woman = (TextView) view.findViewById(R.id.pop_financial);
        //设置contentView
        /* View contentView = LayoutInflater.from(Personal_info.this).inflate(R.layout.sex_item, null);*/
        man.setOnClickListener(new View.OnClickListener() {// 男
            @Override
            public void onClick(View v) {
                final ContentValues values = new ContentValues();
                values.put("sex", man.getText().toString());
                if (cursor.moveToFirst()) {
                    db.update("User", values, "username=?", new String[]{ad});
                }
                try {
                    show_sex.setText(man.getText().toString());
                } catch (Exception e) {

                }
                cursor.close();
                dialog.dismiss();
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {// 女
            @Override
            public void onClick(View v) {
                final ContentValues values = new ContentValues();
                values.put("sex", woman.getText().toString());
                if (cursor.moveToFirst()) {
                    db.update("User", values, "username=?", new String[]{ad});
                }
                cursor.close();
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    /**
     * 日期弹窗
     */
    protected void showDatePickDlg() {
        show_birth = (TextView) findViewById(R.id.show_birth);
        final DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        /*dialog.setMessageFormat("yyyy-MM-dd HH:mm");*/

        dialog.setMessageFormat("yyyy-MM-dd");
        //设置选择回调
        dialog.setOnChangeLisener(null);

        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                dbhelper = new MyDatabasehelper(baby_base_info.this, "User.db", null, 3);
                editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                pref = getSharedPreferences("data", MODE_PRIVATE);
                SimpleDateFormat bartDateFormat = new SimpleDateFormat
                        ("yyyy-MM-dd");
                user = pref.getString("username", "");
                show_birth.setText(bartDateFormat.format(date));
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("birth", bartDateFormat.format(date));
                db.update("User", values, "username=?", new String[]{user});
                editor.putString("birth", bartDateFormat.format(date));
            }
        });
        dialog.show();
    }


    private class baThread implements Runnable {
        SharedPreferences pr = getSharedPreferences("data", MODE_PRIVATE);
        String user = pr.getString("username", "");
        final HashMap<String, String> params = new HashMap<>();

        public HashMap<String, String> getParams() {
            params.put("baby", "query one row");
            params.put("username", user);
            return params;
        }

        @Override
        public void run() {
            try {
                babyList = new ArrayList<>();
                String res = HttpUtil.post(KGURL, getParams());
                JSONArray jsonArray = new JSONArray(res);
                String babyname = null;
                String sex = null;
                String summary = null;
                String relationship = null;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null && jsonArray.length() > 0) {
                        try {
                            sex = URLDecoder.decode(jsonObject.getString("sex"), "utf-8");
                            babyname = URLDecoder.decode(jsonObject.getString("babyname"), "UTF-8");
                            relationship = URLDecoder.decode(jsonObject.getString("relationship"), "utf-8");
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        Baby baby = new Baby(jsonObject.getInt("babyid"), babyname, sex, jsonObject.getString("age"), relationship);
                        babyList.add(baby);
                    } else {
                        Log.i("jsonArray", "error");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!babyList.isEmpty()) {
                Message msg = new Message();
                msg.obj = 1;
                bhandler.sendMessage(msg);
            } else {
                Message msg = new Message();
                msg.obj = 0;
                Log.i("fff", "" + babyList);
                bhandler.sendMessage(msg);
            }
        }
    }

}
