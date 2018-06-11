package com.example.GradeCircle;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.MyInfo.Personal_info;
import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class improve_acc_infomation extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private String user;
    private MyDatabasehelper dbhelper;
    private TextView show_birth;
    private LinearLayout baby_sex;
    private TextView show_sex;
    private LinearLayout baby_relationship;
    private TextView show_relationship;
    private EditText baby_name;
    private EditText parent_phone;
    private EditText baby_class;
    private EditText baby_summary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_acc_infomation);

        ImageView backtograde=(ImageView)findViewById(R.id.backToGrade);
        ImageView edit_tx_grade=(ImageView)findViewById(R.id.edit_tx_grade);
        baby_name=(EditText)findViewById(R.id.baby_name);
        LinearLayout baby_birth=(LinearLayout)findViewById(R.id.baby_birth);
        Button next_grade=(Button)findViewById(R.id.next_grade);
        baby_sex=(LinearLayout) findViewById(R.id.baby_sex);
        show_sex=(TextView)findViewById(R.id.show_sex);
        baby_relationship=(LinearLayout)findViewById(R.id.baby_relationship);
        show_relationship=(TextView)findViewById(R.id.show_relationship);
        parent_phone=(EditText)findViewById(R.id.parent_phone);
        baby_class=(EditText)findViewById(R.id.baby_class);
        baby_summary=(EditText)findViewById(R.id.baby_summary);


        baby_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showrelationsWindow();
            }
        });
        backtograde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(improve_acc_infomation.this, MainActivity.class);
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
                    if(!baby_name.getText().toString().isEmpty()&&!show_sex.getText().toString().isEmpty()&&!show_birth.getText().toString().isEmpty()&&!show_relationship.getText().toString().isEmpty())
                    {
                    Intent intent = new Intent(improve_acc_infomation.this, add_to_nusery.class);
                    intent.putExtra("babyname",baby_name.getText().toString());
                    intent.putExtra("babysex",show_sex.getText().toString());
                    intent.putExtra("babybirth",show_birth.getText().toString());
                    intent.putExtra("relationship",show_relationship.getText().toString());
                    intent.putExtra("parentphone",parent_phone.getText().toString());
                    intent.putExtra("babyclass",baby_class.getText().toString());
                    intent.putExtra("babysummary",baby_summary.getText().toString());
                    startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(improve_acc_infomation.this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2)
        {
            setResult(2);
            finish();
        }
    }

    /**
     * 关系
     */
    private void showrelationsWindow() {
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        final String ad=pref.getString("username","");
        /*MyDatabasehelper dbhelper=new MyDatabasehelper(improve_acc_infomation.this,"User.db",null,3);
        final SQLiteDatabase  db=dbhelper.getWritableDatabase();

        final Cursor cursor=db.rawQuery("select * from User where username=?",new String[]{ad});*/
        AlertDialog.Builder builder = new AlertDialog.Builder(improve_acc_infomation.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(improve_acc_infomation.this, R.layout.relations_item, null);
        final TextView man = (TextView) view.findViewById(R.id.pop_father);
        final TextView woman = (TextView) view.findViewById(R.id.pop_mather);
        //设置contentView
        /* View contentView = LayoutInflater.from(Personal_info.this).inflate(R.layout.sex_item, null);*/
        man.setOnClickListener(new View.OnClickListener() {// 男
            @Override
            public void onClick(View v) {
                final ContentValues values=new ContentValues();
                values.put("sex",man.getText().toString());
                /*if(cursor.moveToFirst())
                {
                    db.update("User",values,"username=?",new String[]{ad});
                }*/
                try {
                    show_relationship.setText(man.getText().toString());
                }catch (Exception e){
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
                }catch (Exception e){
                    e.getMessage();
                }
                /*final ContentValues values=new ContentValues();
                values.put("sex",woman.getText().toString());
                if(cursor.moveToFirst())
                {
                    db.update("User",values,"username=?",new String[]{ad});
                }
                cursor.close();*/
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
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        final String ad=pref.getString("username","");
        MyDatabasehelper dbhelper=new MyDatabasehelper(improve_acc_infomation.this,"User.db",null,3);
        final SQLiteDatabase  db=dbhelper.getWritableDatabase();

        final Cursor cursor=db.rawQuery("select * from User where username=?",new String[]{ad});
        AlertDialog.Builder builder = new AlertDialog.Builder(improve_acc_infomation.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(improve_acc_infomation.this, R.layout.sex_item, null);
        final TextView man = (TextView) view.findViewById(R.id.pop_computer);
        final TextView woman = (TextView) view.findViewById(R.id.pop_financial);
        //设置contentView
        /* View contentView = LayoutInflater.from(Personal_info.this).inflate(R.layout.sex_item, null);*/
        man.setOnClickListener(new View.OnClickListener() {// 男
            @Override
            public void onClick(View v) {
                final ContentValues values=new ContentValues();
                values.put("sex",man.getText().toString());
                if(cursor.moveToFirst())
                {
                    db.update("User",values,"username=?",new String[]{ad});
                }
                try {
                    show_sex.setText(man.getText().toString());
                }catch (Exception e){

                }
                cursor.close();
                dialog.dismiss();
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {// 女
            @Override
            public void onClick(View v) {
                final ContentValues values=new ContentValues();
                values.put("sex",woman.getText().toString());
                if(cursor.moveToFirst())
                {
                    db.update("User",values,"username=?",new String[]{ad});
                }
                try {
                    show_sex.setText(man.getText().toString());
                }catch (Exception e){

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
        show_birth=(TextView)findViewById(R.id.show_birth);
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
                dbhelper=new MyDatabasehelper(improve_acc_infomation.this,"User.db",null,3);
                editor=getSharedPreferences("info",MODE_PRIVATE).edit();
                pref=getSharedPreferences("data",MODE_PRIVATE);
                SimpleDateFormat bartDateFormat = new SimpleDateFormat
                        ("yyyy-MM-dd");
                user=pref.getString("username","");
                show_birth.setText(bartDateFormat.format(date));
                SQLiteDatabase db=dbhelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("birth",bartDateFormat.format(date));
                db.update("User",values,"username=?",new String[]{user});
                editor.putString("birth",bartDateFormat.format(date));
            }
        });
        dialog.show();
    }


}
