package FragmentTest;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.alirezaafkar.toolbar.RtlActionBarDrawerToggle;
import com.alirezaafkar.toolbar.RtlToolbar;
import com.example.GradeCircle.XC_edit;
import com.example.administrator.broadcastbestpractice.MainActivity;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.NewsDetail;
import com.example.administrator.broadcastbestpractice.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import BaseAdapterClass.NewsAdapter;
import Baseclass.News;
import Baseclass.PersonCard;


/**
 * Created by Administrator on 2018/4/6.
 */

public class FragmentPage4 extends Fragment {
    private NewsAdapter newsAdapter;

    private List<News> newsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private Menu menu;
    private MyDatabasehelper dbhelper;
    private TextView title;
    private TextView content;
    private TextView time;
    private TextView username;
    private SimpleDraweeView photo;
    private SharedPreferences pref;
    private int userflag = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        return inflater.inflate(R.layout.fragment4, null);
    }

    @Override
    public void onStart() {
        super.onStart();

        FloatingActionButton fladd = (FloatingActionButton) getActivity().findViewById(R.id.fladd);
        pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int userflag = pref.getInt("userflag", 0);
        if (userflag == 2) {
            fladd.setVisibility(View.VISIBLE);
        }
        init();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("校园公告");
        /* Menu menu=toolbar.getMenu();*/
       /* DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        RtlActionBarDrawerToggle drawerToggle = new RtlActionBarDrawerToggle(getActivity(), drawerLayout,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);*/
       /* drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();*/

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), XC_edit.class);
                try {
                    intent.putExtra("content", content.getText());
                    intent.putExtra("title", title.getText());
                } catch (Exception e) {
                    e.getMessage();
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview);
        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new NewsAdapter(getActivity(), buildData());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                String article = "";
                /* Toast.makeText(getActivity(),"你好recyclerview"+view.getDisplay(),Toast.LENGTH_SHORT).show();*/

                Intent intent = new Intent(getActivity(), NewsDetail.class);
                PersonCard p = buildData().get(postion);
                intent.putExtra("title", p.getTitle());
                intent.putExtra("content", p.getContent());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private List<PersonCard> buildData() {
        /*String[] names = {};
        String[] imgUrs = {};*/

        List<PersonCard> list = new ArrayList<>();
        /*for(int i=0;i<6;i++) {
            PersonCard p = new PersonCard();
            p.avatarUrl = imgUrs[i];
            p.name = names[i];

            *//*p.imgHeight = (i % 2)*100 + 400; *//*//偶数和奇数的图片设置不同的高度，以到达错开的目的
            list.add(p);
        }*/
        dbhelper = new MyDatabasehelper(getActivity(), "Dyna.db", null, 3);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
       /* photo=(SimpleDraweeView)getActivity().findViewById(R.id.user_avatar);
        username=(TextView)getActivity().findViewById(R.id.user_name);
        title=(TextView)getActivity().findViewById(R.id.title_01);
        time=(TextView)getActivity().findViewById(R.id.time_xc);
        content=(TextView)getActivity().findViewById(R.id.content_xc);*/

        Cursor cursor = db.rawQuery("select * from Dyna", null);
        if (cursor.moveToFirst()) {
            do {
                PersonCard p = new PersonCard();
                try {
                    String uri = cursor.getString(cursor.getColumnIndex("uri"));
                    String teachername = cursor.getString(cursor.getColumnIndex("teachername"));
                    String title_01 = cursor.getString(cursor.getColumnIndex("title"));
                    String time_01 = cursor.getString(cursor.getColumnIndex("time"));
                    String content_01 = cursor.getString(cursor.getColumnIndex("content"));
                    p.avatarUrl = uri;
                    p.name = teachername;
                    p.content = content_01;
                    p.title = title_01;
                    p.time = time_01;
                    p.imgHeight = 400;
                    list.add(p);
                } catch (Exception e) {
                    Log.i("ddd", "failed");
                }
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return list;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
