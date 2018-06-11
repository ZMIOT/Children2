package com.example.GradeCircle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.broadcastbestpractice.R;

public class dynamicCircle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_circle);
        Button album=(Button)findViewById(R.id.album);
        Button comment=(Button)findViewById(R.id.comment);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dynamicCircle.this, album_grade.class);
                startActivity(intent);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dynamicCircle.this, comment_grade.class);
                startActivity(intent);
            }
        });
    }
}
