package com.example.MyInfo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
/*import com.bigkoo.pickerview.adapter.NumericWheelAdapter;*/
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.adapters.NumericWheelAdapter;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.view.OnWheelChangedListener;
import com.codbking.widget.view.WheelView;
import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;
import com.example.administrator.broadcastbestpractice.loginActivity;
import com.example.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

import Baseclass.Info;
import Baseclass.parent;



public  class Personal_info extends AppCompatActivity {

    public List<Info> infolist=new ArrayList<>();
    public MyDatabasehelper dbhelper;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private ImageView touxiang;
    public String ip="jdbc:mysql://localhost/";
    public String user="administor";
    public String password="123456";
    public String sql="select * from user_info";
    public String url="jdbc:mysql://localhost/db_children?user=administor&password=123456&useUnicode=true&characterEncoding=8859_1";

    private PopupWindow mPopWindow;

    private String sexinfo;
    private String sex1;
    private SharedPreferences.Editor editor;
    private String birthdate;
    private String user_info;
    private String nickname;
    private String birth;
    private String autograph;
    private TextView show_nickname;
    private TextView show_sex;
    private TextView show_birth;
    private TextView show_autograph;

    private TextView man;
    private TextView woman;

    private Context context;
    protected ViewGroup contentContainer;
    private ViewGroup rootView;//附加View 的 根View
    private ViewGroup dialogView;//附加Dialog 的 根View

    protected PickerOptions mPickerOptions;
    private OnDismissListener onDismissListener;
    private boolean dismissing;

    private Animation outAnim;
    private Animation inAnim;
    private boolean isShowing;

    protected int animGravity = Gravity.BOTTOM;

    private Dialog mDialog;
    protected View clickView;//是通过哪个View弹出的
    private boolean isAnim = true;
    private  String admin;

    private ArrayList<String> optionsItems=new ArrayList<>();


    private  SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        LinearLayout myName = (LinearLayout) findViewById(R.id.myName);
        LinearLayout birth = (LinearLayout) findViewById(R.id.birth);
        LinearLayout sex = (LinearLayout) findViewById(R.id.sex);
        LinearLayout autography = (LinearLayout) findViewById(R.id.auto);

        ImageView backToMine = (ImageView) findViewById(R.id.backToMine);

        backToMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Personal_info.this, MainActivity.class);
                startActivity(intent);
            }
        });
        autography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Personal_info.this, autograph.class);
                startActivity(intent);
            }
        });
        myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
                Intent intent = new Intent(Personal_info.this, com.example.MyInfo.myName.class);
                startActivity(intent);
            }

        });
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }

        });

        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsexWindow();
            }
        });
        showMessage();
    }
    private void showsexWindow() {
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        final String ad=pref.getString("username","");
        MyDatabasehelper dbhelper=new MyDatabasehelper(Personal_info.this,"User.db",null,3);
        final SQLiteDatabase  db=dbhelper.getWritableDatabase();

        final Cursor cursor=db.rawQuery("select * from User where username=?",new String[]{ad});
        AlertDialog.Builder builder = new AlertDialog.Builder(Personal_info.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(Personal_info.this, R.layout.sex_item, null);
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
                showMessage();
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
                /*try {
                    show_sex.setText(woman.getText().toString());
                }catch (Exception e){

                }*/
                showMessage();
                cursor.close();
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();

    }

    public void show(View v) {
        this.clickView = v;
        show();
    }
    /**
     * 添加View到根视图
     */
    public void show() {
        if (isDialog()) {
            showDialog();
        } else {
            if (isShowing()) {
                return;
            }
            isShowing = true;
            onAttached(rootView);
            rootView.requestFocus();
        }
    }
    public boolean isDialog() {
        return false;
    }
    private void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }
    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        if (isDialog()) {
            return false;
        } else {
            return rootView.getParent() != null || isShowing;
        }

    }
    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        mPickerOptions.decorView.addView(view);
        if (isAnim) {
            contentContainer.startAnimation(inAnim);
        }
    }
    public void showInfo() {
            show_nickname=(TextView)findViewById(R.id.show_myName);
            show_sex=(TextView)findViewById(R.id.show_sex);
            show_autograph=(TextView)findViewById(R.id.show_auto);
            pref=getSharedPreferences("data",MODE_PRIVATE);
            String username=pref.getString("username","");

        }


    @Override
    protected void onRestart() {
        super.onRestart();
        showMessage();
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
                editor=getSharedPreferences("info",MODE_PRIVATE).edit();
                pref=getSharedPreferences("data",MODE_PRIVATE);
                SimpleDateFormat bartDateFormat = new SimpleDateFormat
                        ("yyyy-MM-dd");
                admin=pref.getString("username","");
                show_birth.setText(bartDateFormat.format(date));
                SQLiteDatabase db=dbhelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("birth",bartDateFormat.format(date));
                db.update("User",values,"username=?",new String[]{admin});
                editor.putString("birth",bartDateFormat.format(date));
            }
        });
        dialog.show();
    }

    /**
     * 将数据库中的个人资料信息取出并显示出来
     */
    protected void showMessage(){

        TextView show_nickname=(TextView)findViewById(R.id.show_myName);
        TextView show_sex=(TextView)findViewById(R.id.show_sex);
        TextView show_birth=(TextView)findViewById(R.id.show_birth);
        TextView show_autograph=(TextView)findViewById(R.id.show_auto);
        pref=getSharedPreferences("data",MODE_PRIVATE);
       SharedPreferences pref1=getSharedPreferences("info",MODE_PRIVATE);
        String username=pref.getString("username","");
        sex1=pref1.getString("sex","");
        birthdate=pref1.getString("birth","");
        autograph=pref1.getString("autograph","");
        dbhelper=new MyDatabasehelper(this,"User.db",null,3);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Log.i("username",username);
        Cursor cursor=db.rawQuery("select * from User where username=?",new String[]{username});

        if(cursor.moveToFirst()){

            if(cursor!=null){
                do{
                    String nickname=cursor.getString(cursor.getColumnIndex("nickname"));
                    String birthday=cursor.getString(cursor.getColumnIndex("birth"));
                    String sexes=cursor.getString(cursor.getColumnIndex("sex"));
                    String autograph=cursor.getString(cursor.getColumnIndex("autograph"));
                    show_nickname.setText(nickname);
                    show_birth.setText(birthday);
                    show_sex.setText(sexes);
                    show_autograph.setText(autograph);
                }
                while(cursor.moveToNext());
            }
        }
        else{
            Toast.makeText(Personal_info.this,"哈哈",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });
    }

    /**
     * 头像信息初始化
     */
    private void initView() {
        touxiang = (ImageView)findViewById(R.id.edit_tx);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            touxiang.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }
    /***
     * 编辑头像
     */
    protected void showTypeDialog(){
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(Personal_info.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(Personal_info.this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    /**
     * StartActivityForResult的回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        MyDatabasehelper dbhelper=new MyDatabasehelper(Personal_info.this,"User.db",null,3);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        String adm=pref.getString("username","");
        Context mContext=Personal_info.this;
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        head.compress(Bitmap.CompressFormat.PNG, 100, os);
                        if(os.toByteArray()!=null){
                            values.put("touxiang",os.toByteArray());
                            db.update("User",values,"username=?",new String[]{adm});
                        }
                        else
                        {
                            Drawable drawable = context.getResources().getDrawable(R.drawable.tx);
                            values.put("touxiang",getPicture(drawable));
                            db.update("User",values,"username=?",new String[]{adm});
                        }
                        setPicToView(head);// 保存在SD卡中
                        touxiang.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //将drawable转换成可以用来存储的byte[]类型

    private byte[] getPicture(Drawable drawable) {
        if(drawable == null) {
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }




    /**
     * 调用系统的裁剪功能
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




