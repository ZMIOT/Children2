package com.example.GradeCircle;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alirezaafkar.toolbar.RtlToolbar;
import com.example.administrator.broadcastbestpractice.R;

public class grade_info_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_info_detail);
        TextView title = (TextView) findViewById(R.id.title_bj_detail);
        TextView article = (TextView) findViewById(R.id.article_bj_detail);
        Intent intent = getIntent();
        String name = intent.getStringExtra("title");
        String text = intent.getStringExtra("content");
        title.setTextSize(16);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setText(name);
        article.setText(text);
    }
}
