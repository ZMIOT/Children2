package com.example.GradeCircle;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;

import Baseclass.Comment;

public class Comment_detail extends AppCompatActivity {
private SharedPreferences pref;
private EditText commentArea;
private TextView teachernameArea;
private  MyDatabasehelper dbhelper=new MyDatabasehelper(Comment_detail.this,"Comment_TB.db",null,3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        pref=getSharedPreferences("zm",MODE_PRIVATE);
       /* final EditText commentArea=(EditText) findViewById(R.id.comment_content_text);*/
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_teachername);
       /* TextView tea=(TextView)findViewById(R.id.teachername);*/
        ImageView comment_detail_bg=(ImageView)findViewById(R.id.comment_detail_bg_view);
        Glide.with(getApplicationContext()).load(R.mipmap.bg_ios).into(comment_detail_bg);


        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        WebView webView=(WebView)findViewById(R.id.wv_detail_content);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        toolbar.setTitle("详情");
        /*tea.setText(teachername);
        commentArea.setText(comment);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
               finish();
               return true;
       }
       return super.onOptionsItemSelected(item);
   }
}

