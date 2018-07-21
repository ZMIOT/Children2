package com.example.administrator.broadcastbestpractice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import FragmentTest.FragmentPage1;
import FragmentTest.FragmentPage2;
import FragmentTest.FragmentPage3;
import FragmentTest.FragmentPage4;
import FragmentTest.FragmentPage5;
import utils.DataStorageUtils;


public class MainActivity extends BaseActivity {
    private FragmentTabHost fragmentTabHost;
    private String texts1[] = {"班级圈", "公告", "管理", "我"};
    private String texts[] = {"班级圈", "公告", "我"};
    /*private int imageButton[] = { R.drawable.persons,
            R.drawable.information ,R.drawable.person};*/
    private int imageButton1[] = {R.drawable.persons,
            R.drawable.information, R.drawable.administrator, R.drawable.person};
    /* private Class fragmentArray[] = {FragmentPage1.class,FragmentPage4.class, FragmentPage5.class};*/

    private Class fragmentArray1[] = {FragmentPage1.class, FragmentPage4.class, FragmentPage3.class, FragmentPage5.class};

    private MyDatabasehelper dbhelper;
    private SharedPreferences pref1;
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;
    TabHost.TabSpec spec;
    private int userflag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 实例化tabhost
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(),
                R.id.maincontent);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        dbhelper = new MyDatabasehelper(MainActivity.this, "User.db", null, 3);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Intent intent = getIntent();
        userflag = intent.getIntExtra("userflag", 0);
        int jug = intent.getIntExtra("state", 0);

       /* if (userflag==0||userflag==1)//为家长和教师的时候
        {
            if(jug==1) {
                spec = fragmentTabHost.newTabSpec(texts[0]).setIndicator(getView(0));
                fragmentTabHost.addTab(spec, FragmentPage2.class, null);

                //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
                fragmentTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bt_selector);
            }
            else {
                spec = fragmentTabHost.newTabSpec(texts[0]).setIndicator(getView(0));
                fragmentTabHost.addTab(spec, fragmentArray[0], null);

                //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
                fragmentTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bt_selector);
            }

            for (int i = 1; i < texts.length; i++) {
                spec=fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));

                fragmentTabHost.addTab(spec, fragmentArray[i], null);

                //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
                fragmentTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bt_selector);
            }
        }

        *//**
         * 当角色为管理员的时候
         *//*
        else
        {*/
        if (jug == 1 || userflag == 1 || userflag == 2) {
            spec = fragmentTabHost.newTabSpec(texts[0]).setIndicator(getView(0));
            fragmentTabHost.addTab(spec, FragmentPage2.class, null);

            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            fragmentTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bt_selector);
        } else {
            spec = fragmentTabHost.newTabSpec(texts[0]).setIndicator(getView(0));
            fragmentTabHost.addTab(spec, fragmentArray1[0], null);
            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            fragmentTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bt_selector);
        }
        for (int i = 1; i < texts1.length; i++) {
            spec = fragmentTabHost.newTabSpec(texts1[i]).setIndicator(getView(i));

            fragmentTabHost.addTab(spec, fragmentArray1[i], null);

            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            fragmentTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bt_selector);
        }
        /*}*/
    }

    @Override
    protected void onResume() {
        int id = getIntent().getIntExtra("id", 0);
        if (id == 2) {
            Fragment fragment = new FragmentPage2();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.fragment2, fragment);
            transaction.commit();
            //帮助跳转到指定子fragment
            Intent i = new Intent();
            i.setClass(MainActivity.this, FragmentPage2.class);
            i.putExtra("id", 2);
        }

        super.onResume();
    }

    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.tabcontent, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(imageButton1[i]);
        textView.setText(texts1[i]);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {
            spec = fragmentTabHost.newTabSpec(texts1[1]).setIndicator(getView(1));
            fragmentTabHost.addTab(spec, FragmentPage4.class, null);
            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            fragmentTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.bt_selector);
        }
    }


}
