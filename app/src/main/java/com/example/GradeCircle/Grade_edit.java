package com.example.GradeCircle;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;

import net.bither.util.NativeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import BaseAdapterClass.GridViewAddImgesAdpter;
import utils.HttpUtil;

public class Grade_edit extends AppCompatActivity implements View.OnClickListener {
    private GridView gw;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private Dialog dialog;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    /* 头像名称 */
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private TextView save;
    private EditText title;
    private EditText edit_xc_info;
    private TextView cancel;
    private List<Uri> uriList;
    private List<String> slist;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/CommentLet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_edit);
        /*Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_grade_edit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);*/
        TextView cancel = (TextView) findViewById(R.id.cancel_grade_edit);
        gw = (GridView) findViewById(R.id.gw_grade);
        datas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        try {
            gw.setAdapter(gridViewAddImgesAdpter);//和XC_detail公用一个适配器
        } catch (Exception e) {
            e.getMessage();
        }
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showdialog();
            }
        });
        save = (TextView) findViewById(R.id.btn_grade);
        title = (EditText) findViewById(R.id.title_grade);
        edit_xc_info = (EditText) findViewById(R.id.edit_grade_info);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    //实现save的点击事件
    @Override
    public void onClick(View v) {
        savetoDB(uriList);
        /* new Thread(new imgThread()).start();*/
        Intent intent = new Intent();
        intent.putExtra("uris", uriList.toArray());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 选择图片对话框
     */
    public void showdialog() {
        View localView = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_picture, null);//和XC_detail公用一个弹出视图
        TextView tv_camera = (TextView) localView.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) localView.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 拍照
                camera();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                gallery();
            }
        });
    }

    /**
     * 拍照
     */
    public void camera() {
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {

            File dir = new File(IMAGE_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            tempFile = new File(dir,
                    System.currentTimeMillis() + "_" + PHOTO_FILE_NAME);
            //从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            Intent intent = new Intent();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(intent.CATEGORY_DEFAULT);
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        } else {
            Toast.makeText(this, "未找到存储卡，无法拍照！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 从相册获取2
     */
    public void gallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径

                    Uri uri = data.getData();
                    //存储到本地数据库
                    uriList.add(uri);
                    /* slist.add(uri);*/
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);

                    uploadImage(path);
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                if (resultCode != RESULT_CANCELED) {
                    // 从相机返回的数据
                    if (hasSdcard()) {
                        if (tempFile != null) {
                            uploadImage(tempFile.getPath());
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                        }

                        Log.i("images", "拿到照片path=" + tempFile.getPath());
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }
        }
    };

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                if (new File(path).exists()) {
                    Log.d("images", "源文件存在" + path);
                } else {
                    Log.d("images", "源文件不存在" + path);
                }

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50);
                if (file.exists()) {
                    Log.d("images", "压缩后的文件存在" + file.getAbsolutePath());
                } else {
                    Log.d("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);
            }
        }.start();
    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }

    public void savetoDB(List<Uri> uris) {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String user = pref.getString("username", "");
        MyDatabasehelper dbhelper = new MyDatabasehelper(Grade_edit.this, "Grade.db", null, 3);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        /*  Cursor cursor1=db.rawQuery("select*from Dyna where teachername=?",new String[]{"zm"});
        if(cursor1.moveToFirst())
        {
            contentValues.put("uri",uri.toString());
            db.update("Dyna",contentValues,"teachername=?",new String[]{"zm"});
            db.close();
        }else
        {*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        int count;
        /*for(count=0;count<uris.size();count++) {
            contentValues.put("uris",);
        }*/
        /*contentValues.put("count",count);*/
        contentValues.put("title", title.getText().toString());
        contentValues.put("content", edit_xc_info.getText().toString());
        contentValues.put("time", time);
        contentValues.put("teachername", user);
        db.insert("Grade", null, contentValues);
        db.close();
        /*}*/
    }

    /*private void jsonForm1() {
        JSONArray imgArray = new JSONArray();
        int i = uriList.size();
        for (int j = 0; j < i; j++) {
            try {
                JSONObject stoneObject = new JSONObject();
                stoneObject.put("simg", uriList.get(j).getName());
                stoneObject.put("img", uriList.get(j).getNumber());
                imgArray.put(stoneObject);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        Map<String, String> datas = new HashMap<String , String>();
        datas.put("paramJson", imgArray.toString());
    }*/


    /*private class imgThread implements Runnable{
        final HashMap<String, String> params = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        public HashMap<String, String> getParams() {
            params.put("dyna","insert");
            params.put("title",title.getText().toString());
            params.put("content",edit_xc_info.getText().toString());
            params.put("time",time);

            return params;
        }
        @Override
        public void run() {
           String info= HttpUtil.post(KGURL,)
        }
    }*/
}