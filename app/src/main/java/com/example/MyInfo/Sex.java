package com.example.MyInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.broadcastbestpractice.R;

public class Sex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
       /* ImageView backToMyInfo=(ImageView) findViewById(R.id.backToMyInfo_sex);
        TextView save_sex=(TextView)findViewById(R.id.save_sex);
       backToMyInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sex.this,Personal_info.class);
                startActivity(intent);
            }
        });
        save_sex.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });*/
    }
}
