package com.example.administrator.broadcastbestpractice;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alirezaafkar.toolbar.RtlToolbar;


public class NewsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        TextView article=(TextView)findViewById(R.id.article);
        Intent intent=getIntent();
        String name=intent.getStringExtra("title");
        String text=intent.getStringExtra("content");
        TextView title=(TextView)findViewById(R.id.title);
        title.setTextSize(16);
        title .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setText(name);
        article.setText(text);
    }

}
