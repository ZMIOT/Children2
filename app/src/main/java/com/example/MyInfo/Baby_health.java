package com.example.MyInfo;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.broadcastbestpractice.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Baseclass.Baby;
import Baseclass.BabyHealth;
import utils.HttpUtil;
import utils.MyXFormatter;

public class Baby_health extends AppCompatActivity {
   private BarChart barChart;
   private LineChart lineChart;
   private String[] values={"mon","thur","wed","thes","fri","sur","sun"};
   private String URL="http://192.168.43.143:8081/HelloWeb/BabyHealth";
   private SharedPreferences pref;
   private Handler babyHealthHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_health);
        new Thread(new BabyHealthThread());


        /**
         * 重要的是获得jsonArray的对象，并且将其转化为jsonObject的对象
         *jsonArray中的数据形式[{name:"zm",sex:"男"},{},{},{},{}]
         * {name:"zm",sex:"男"}是一个jsonObject对象
         */
        babyHealthHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String res=(String)msg.obj;
                try {
                    JSONArray jsonArray =new JSONArray(res);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);//jsonobject和jsonarray之间的转换
                        jsonObject.getInt("babyid");
                        jsonObject.getString("babyname");
                        jsonObject.getInt("BHeartRate");
                        jsonObject.getInt("BBloodPressure");
                    }
                }catch (Exception e){
                    e.getMessage();
                }
            }
        };

        barChart=(BarChart)findViewById(R.id.barchart);
        lineChart=(LineChart)findViewById(R.id.month_chart);

        List<Entry> month=new ArrayList<>();
        List<BarEntry> day = new ArrayList<>();


        day.add(new BarEntry(0f, 30f));
        day.add(new BarEntry(1f, 80f));
        day.add(new BarEntry(2f, 60f));
        day.add(new BarEntry(3f, 50f));
        // gap of 2f
        day.add(new BarEntry(4f, 70f));
        day.add(new BarEntry(5f, 60f));
        day.add(new BarEntry(6f, 40f));

        List<BabyHealth> babyHealths=new ArrayList<>();
        BabyHealth babyHealth1=new BabyHealth(1,70);
        BabyHealth babyHealth2=new BabyHealth(2,89);
        BabyHealth babyHealth3=new BabyHealth(3,75);
        BabyHealth babyHealth4=new BabyHealth(4,90);
        babyHealths.add(babyHealth1);
        babyHealths.add(babyHealth2);
        babyHealths.add(babyHealth3);
        babyHealths.add(babyHealth4);
        List<Entry> entries = new ArrayList<Entry>();
        for (int i=0;i<babyHealths.size();i++)
        {
            entries.add(new Entry(babyHealths.get(i).getCordRateX(),babyHealths.get(i).getCordRateY()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "心率"); // add entries to dataset
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        /*dataSet.setValueTextColor(...); // styling, ...*/
        MyXFormatter formatter=new MyXFormatter(values);
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawAxisLine(false);
       /* xAxis.setDrawGridLines(false);*/
      /*  xAxis.setLabelCount(7);*/
        xAxis.setValueFormatter(formatter);

        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

        BarDataSet set = new BarDataSet(day, "血压");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); //设置自定义条形宽度
        barChart.setData(data);
        barChart.setFitBars(true); //使x轴完全适合所有条形
        barChart.invalidate(); // refresh

    }
   //通过hashmap封装请求字段
    private HashMap<String,String> getParams(){
        pref=getSharedPreferences("data",MODE_PRIVATE);
        String parentname=pref.getString("username","");
        HashMap<String,String> params=new HashMap<>();
        params.put("query","BabyHealth");
        params.put("parentname",parentname);
        return params;
    }

    //线程请求后台数据
    private class BabyHealthThread implements Runnable{
        Message msg=new Message();
        @Override
        public void run() {
            String info= HttpUtil.post(URL,getParams());
            if(!"".equals(info)){
                msg.obj=info;
            }
            else
            {
                msg.obj="empty";
            }
            babyHealthHandler.sendMessage(msg);
        }
    }

}
