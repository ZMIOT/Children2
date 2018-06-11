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
import com.example.InfoManagement.Baby_info;
import com.example.InfoManagement.Teacher_info;
import com.example.administrator.broadcastbestpractice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BaseAdapterClass.BabyAdapter;
import BaseAdapterClass.TeacherAdapter;
import Baseclass.Baby;
import Baseclass.Teacher;
import utils.HttpUtil;


public class ThreeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Baby> babyList;
    private static final String KGURL = "http://192.168.43.143:8081/HelloWeb/BabyLet";
    private Handler babyHandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.threefragment, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView=(RecyclerView)getActivity().findViewById(R.id.recyclerview_baby_info);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new Thread(new babyThread()).start();
        babyHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch ((int)msg.obj)
                {
                    case 1:
                        BabyAdapter babyAdapter=new BabyAdapter(R.layout.baby_item,babyList);
                        mRecyclerView.setAdapter(babyAdapter);
                        babyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent=new Intent(getActivity(),Baby_info.class);
                                intent.putExtra("babyname",babyList.get(position).getBabyname());
                                intent.putExtra("parent_phone",babyList.get(position).getParent_phone());
                                intent.putExtra("summary",babyList.get(position).getSummary());
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
    }
    public class babyThread implements Runnable{

        final HashMap<String, String> params = new HashMap<>();
        public HashMap<String, String> getParams() {
            params.put("baby","query");
            return params;
        }
        @Override
        public void run() {
            try {
                babyList=new ArrayList<>();
                String res = HttpUtil.post(KGURL,getParams());
                JSONArray jsonArray =new JSONArray(res);
                String babyname=null;
                String classname=null;
                String summary=null;
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject!=null && jsonArray.length()>0)
                    {
                       /* Baby baby=new Baby(jsonObject.getInt("babyid"),jsonObject.getString("babyname"),jsonObject.getString("sex"),jsonObject.getString("birth"),jsonObject.getString("parent"),jsonObject.getString("summary"),jsonObject.getString("phone"),
                                jsonObject.getInt("classid"),jsonObject.getString("summary"),jsonObject.getString("parent_phone"));*/
                       try {
                            babyname = URLDecoder.decode(jsonObject.getString("babyname"), "UTF-8");
                            classname = URLDecoder.decode(jsonObject.getString("classname"), "UTF-8");
                           summary = URLDecoder.decode(jsonObject.getString("summary"), "UTF-8");
                       }catch (Exception e)
                       {
                           e.getMessage();
                       }
                        Baby baby=new Baby(jsonObject.getInt("babyid"),babyname,classname,summary,jsonObject.getString("parent_phone"));
                        babyList.add(baby);
                    }
                    else
                    {
                        Log.i("jsonArray","error");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!babyList.isEmpty()){
                Message msg=new Message();
                msg.obj=1;
                babyHandler.sendMessage(msg);
            }


        }
    }
}
