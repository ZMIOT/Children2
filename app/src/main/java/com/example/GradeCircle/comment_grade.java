package com.example.GradeCircle;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;
import com.example.administrator.broadcastbestpractice.loginActivity;
import com.sun.jna.platform.win32.WinNT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BaseAdapterClass.commentAdapter;
import Baseclass.Comment;
import utils.HttpUtil;

public class comment_grade extends AppCompatActivity {

    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/CommentLet";
    private List<Comment> comments = new ArrayList<>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private commentAdapter commentadapter;
    private int count = 0;
    private RecyclerView mRecyclerView;
    private Handler commentHandler;
    private RecyclerView.LayoutManager mLayoutManager;
    private View notDataView;

    private boolean mError = true;
    private boolean mNoData = true;
    private int userflag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_grade);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_comment);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new Thread(new commentThread()).start();
        commentHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch ((int) msg.obj) {
                    case 1: {
                        commentadapter = new commentAdapter(R.layout.comment_item, comments);
                        mRecyclerView.setAdapter(commentadapter);
                        commentadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(comment_grade.this, Comment_detail.class);
                                intent.putExtra("teachername", comments.get(position).getTeachername());
                                intent.putExtra("babyname", comments.get(position).getBabyname());
                                /*intent.putExtra("comment",comments.get(position).getComment());*/
                                intent.putExtra("url", comments.get(position).getUrl());
                                startActivity(intent);
                            }
                        });

                        commentadapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                                new AlertDialog.Builder(comment_grade.this)
                                        .setTitle("确认要删除吗")
                                        .setMessage("确定吗？")
                                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                comments.get(position).getTeacherid();
                                            }
                                        })
                                        .setNegativeButton("否", null)
                                        .show();
                                return false;
                            }
                        });

                    }
                    break;
                    case 0:
                        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
                        notDataView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onRefresh();
                            }
                        });
                        break;
                }
            }
        };
        pref = getSharedPreferences("data", MODE_PRIVATE);
        userflag = pref.getInt("userflag", 0);
        FloatingActionButton commentFab = (FloatingActionButton) findViewById(R.id.comment_info_add);
        if (userflag == 1) {
            commentFab.setVisibility(View.VISIBLE);
        }
        commentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(comment_grade.this, Comment_add.class);
                startActivity(intent);
            }
        });
    }

    private void onRefresh() {
        commentadapter.setEmptyView(R.layout.load_view, (ViewGroup) mRecyclerView.getParent());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    commentadapter.setEmptyView(notDataView);
                    mNoData = false;
                } else {
                    commentadapter.setNewData(comments);
                }
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class commentThread implements Runnable {
        final HashMap<String, String> params = new HashMap<>();

        public HashMap<String, String> getParams() {
            params.put("comment", "query");
            return params;
        }

        @Override
        public void run() {
            try {
                String res = HttpUtil.post(KGURL, getParams());
                JSONArray jsonArray = new JSONArray(res);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null && jsonArray.length() > 0) {
                        String urlStr = null;
                        try {
                            urlStr = java.net.URLDecoder.decode(jsonObject.getString("url"), "utf-8");
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        Comment comment = new Comment(jsonObject.getInt("teacherid"), urlStr, jsonObject.getString("babyname"), jsonObject.getString("teachername"));
                        comments.add(comment);
                    } else {
                        Log.i("jsonArray", "error");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!comments.isEmpty()) {
                Message msg = new Message();
                msg.obj = 1;
                commentHandler.sendMessage(msg);
            } else {
                Message msg = new Message();
                msg.obj = 0;
                commentHandler.sendMessage(msg);
            }

        }
    }

}
