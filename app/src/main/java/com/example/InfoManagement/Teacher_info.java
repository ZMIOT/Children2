package com.example.InfoManagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.broadcastbestpractice.R;

public class Teacher_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        final TextView summaryArea=(TextView) findViewById(R.id.teacher_content_text);
        Toolbar toolbar=(Toolbar)findViewById(R.id.teacher_toolbar);
        TextView tea=(TextView)findViewById(R.id.teacher_name);
        ImageView comment_detail_bg=(ImageView)findViewById(R.id.teacher_detail_bg_view);

        Intent intent=getIntent();
        String teachername=intent.getStringExtra("teachername");
        String teacherphone=intent.getStringExtra("teacherphone");
        String summary=intent.getStringExtra("summary");

        toolbar.setTitle(teachername);
        tea.setText(teacherphone);
        summaryArea.setText(summary);
        Glide.with(getApplicationContext()).load(R.mipmap.bg_ios).into(comment_detail_bg);
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
