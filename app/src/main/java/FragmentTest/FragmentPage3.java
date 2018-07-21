package FragmentTest;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.broadcastbestpractice.MyBroadReceiver;
import com.example.administrator.broadcastbestpractice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BaseAdapterClass.TeacherAdapter;
import Baseclass.Teacher;
import utils.HttpUtil;

public class FragmentPage3 extends Fragment {
    /**
     * PagerSlidingTabStrip的实例
     */
    private PagerSlidingTabStrip tabs;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private OneFragment oneFragment;
    private ThreeFragment threeFragment;
    private EmptyFragment emptyFragment;

    private MyBroadReceiver myBroadReceiver;
    private int userflag = 0;
    private SharedPreferences pref;
    private int flag = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Teacher> teacherList;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/TeacherLet";
    private static final String KGURL1 = "http://192.168.43.143:8081/HelloWeb/BabyLet";
    private Handler frageHandler;
    private TeacherAdapter teacherAdapter;
    private View notDataView;
    private int FLAG = 0;
    private int bFLAG = 0;
    private ViewPager pager;
    private int state = 0;
    /*
     *
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, null);

        setOverflowShowingAlways();

        dm = getResources().getDisplayMetrics();
        pager = (ViewPager) view.findViewById(R.id.admin_pager);
        pager.setOffscreenPageLimit(0);//设置ViewPager的缓存界面数,默认缓存为2
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        new Thread(new fragmentThread()).start();
        frageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int[] i = (int[]) msg.obj;
                if (i[0] == 1) {
                    FLAG = 1;
                    if (i[1] == 1) {
                        bFLAG = 1;
                    }
                    pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
                    tabs.setViewPager(pager);
                    setTabsValue();
                } else {
                    if (i[1] == 1) {
                        bFLAG = 1;
                    }
                    pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
                    tabs.setViewPager(pager);
                    setTabsValue();
                }
               /* switch(i[0])
                {
                    case 1: {
                        FLAG = 1;
                        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
                        tabs.setViewPager(pager);
                        setTabsValue();
                        break;
                    }
                    case 0:
                        FLAG=0;
                       //notDataView = getLayoutInflater().inflate(R.layout.empty_view,pager, false);
                        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
                        tabs.setViewPager(pager);
                        setTabsValue();
                        *//* notDataView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onRefresh();
                            }
                        });*//*
                        break;
                    default:
                        break;
                }*/

            }

        };


        return view;
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {

        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));

        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#d83737"));//#d83737   #d83737(绿)
       /* // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(Color.parseColor("#ffffff"));*/
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"教师信息", "幼儿信息"};
        private final String[] titles1 = {"教师信息"};
        private final String[] titles2 = {"幼儿信息"};
        private SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        @Override
        public CharSequence getPageTitle(int position) {
            userflag = pref.getInt("userflag", 0);
            if (userflag == 2) {
                return titles[position];
            } else if (userflag == 1) {
                return titles2[position];
            } else
                return titles1[position];
        }

        @Override
        public int getCount() {
            userflag = pref.getInt("userflag", 0);
            if (userflag == 2) {
                return titles.length;
            } else if (userflag == 1) {
                return titles2.length;
            } else {
                return titles1.length;
            }
        }


        @Override
        public Fragment getItem(int position) {
            state = pref.getInt("state", 0);
            userflag = pref.getInt("userflag", 0);
            if (state == 0 && userflag == 0) {  //当为家长，且并没有绑定是，是不能显示任何数据的
                emptyFragment = new EmptyFragment();
                return emptyFragment;
            } else {
                if (position == 0 && (userflag == 0 || userflag == 2) && oneFragment == null) {
                    if (FLAG == 0) {
                        emptyFragment = new EmptyFragment();
                        return emptyFragment;
                    } else {
                        oneFragment = new OneFragment();
                        return oneFragment;
                    }
                }

                if (position == 1 && (userflag == 1 || userflag == 2) && threeFragment == null) {
                    if (bFLAG == 0) {
                        emptyFragment = new EmptyFragment();
                        return emptyFragment;
                    } else {
                        threeFragment = new ThreeFragment();
                        return threeFragment;
                    }
                }
                if (position == 0 && (userflag == 1) && threeFragment == null) {
                    if (bFLAG == 0) {
                        emptyFragment = new EmptyFragment();
                        return emptyFragment;
                    } else {
                        threeFragment = new ThreeFragment();
                        return threeFragment;
                    }
                } else {
                    return null;
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private class fragmentThread implements Runnable {
        final HashMap<String, String> params = new HashMap<>();

        public HashMap<String, String> getParams() {
            params.put("key", "teacher");
            return params;
        }

        public HashMap<String, String> getParams1() {
            params.put("baby", "query");
            return params;
        }

        @Override
        public void run() {
            /*try {*/
            teacherList = new ArrayList<>();
            String res = HttpUtil.post(KGURL, getParams());
            String inf = HttpUtil.post(KGURL1, getParams1());
            int i[] = {0, 0};
            if (!"]".equals(res)) {
                i[0] = 1;
                if (!"]".equals(inf)) {
                    i[1] = 1;
                    Message msg = new Message();
                    msg.obj = i;
                    frageHandler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.obj = i;
                    frageHandler.sendMessage(msg);
                }
            } else {
                if (!"]".equals(inf)) {
                    i[1] = 1;
                    Message msg = new Message();
                    msg.obj = i;
                    frageHandler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.obj = i;
                    frageHandler.sendMessage(msg);
                }

            }
        }
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(getParentFragment().getActivity());
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
