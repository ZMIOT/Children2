package FragmentTest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.GradeCircle.Comment_detail;
import com.example.GradeCircle.comment_grade;
import com.example.InfoManagement.Teacher_info;
import com.example.administrator.broadcastbestpractice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BaseAdapterClass.TeacherAdapter;
import Baseclass.Comment;
import Baseclass.Teacher;
import utils.HttpUtil;


public class OneFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Teacher> teacherList;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/TeacherLet";
    private Handler teacherHandler;
    private TeacherAdapter teacherAdapter;
    private View notDataView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onefragment, null);
    }
    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView=(RecyclerView)getActivity().findViewById(R.id.recyclerview_teacher_info);
        /*mRecyclerView.setHasFixedSize(true);*/
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new Thread(new teacherThread()).start();
        teacherHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch ((int)msg.obj)
                {
                    case 1:
                        TeacherAdapter teacherAdapter=new TeacherAdapter(R.layout.teacher_item,teacherList,getView());
                        mRecyclerView.setAdapter(teacherAdapter);
                        teacherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent=new Intent(getActivity(),Teacher_info.class);
                                intent.putExtra("teachername",teacherList.get(position).getName());
                                intent.putExtra("teacherphone",teacherList.get(position).getPhone());
                                intent.putExtra("summary",teacherList.get(position).getSummary());
                                startActivity(intent);
                            }
                        });
                    case 0:
                        mRecyclerView.setHasFixedSize(true);
                        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);


                        /* notDataView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onRefresh();
                            }
                        });*/
                        break;
                        default:
                            break;
                }
            }
        };


    }

    public class teacherThread implements Runnable{

        final HashMap<String, String> params = new HashMap<>();
        public HashMap<String, String> getParams() {
            params.put("key","teacher");
            return params;
        }
        @Override
        public void run() {
            try {
                teacherList=new ArrayList<>();
                String res = HttpUtil.post(KGURL,getParams());
                JSONArray jsonArray =new JSONArray(res);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject!=null && jsonArray.length()>0) {
                        try{
                            URLDecoder.decode(jsonObject.getString("teachername"),"utf-8");
                            URLDecoder.decode(jsonObject.getString("sex"),"utf-8");
                            URLDecoder.decode(jsonObject.getString("summary"),"utf-8");
                        }catch (Exception e)
                        {
                            e.getMessage();
                        }
                        Teacher teacher=new Teacher(jsonObject.getString("teachername"),jsonObject.getString("sex"),jsonObject.getInt("age"),jsonObject.getString("summary"),jsonObject.getString("phone"));
                        teacherList.add(teacher);
                    }
                    else
                    {
                        Log.i("jsonArray","error");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!teacherList.isEmpty()){
                Message msg=new Message();
                msg.obj=1;
                teacherHandler.sendMessage(msg);
            }
            else
            {
                Message msg=new Message();
                msg.obj=0;
                teacherHandler.sendMessage(msg);
            }

        }
    }
   /* private void onRefresh() {
        teacherAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerView.getParent());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mError) {
                    mQuickAdapter.setEmptyView(errorView);
                    mError = false;
                } else {
                    if (mNoData) {
                        mQuickAdapter.setEmptyView(notDataView);
                        mNoData = false;
                    } else {
                        mQuickAdapter.setNewData(DataServer.getSampleData(10));
                    }
                }
            }
        }, 1000);
    }*/
}
