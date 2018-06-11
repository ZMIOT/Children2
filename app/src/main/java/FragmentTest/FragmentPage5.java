package FragmentTest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.MyInfo.Baby_health;
import com.example.MyInfo.Personal_info;
import com.example.MyInfo.Update_password;
import com.example.MyInfo.admin_info;
import com.example.MyInfo.baby_base_info;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;
import com.example.MyInfo.Set_up;

/**
 * Created by Administrator on 2018/4/6.
 */

public class FragmentPage5 extends Fragment {

    private LinearLayout baby_info;
    private MyDatabasehelper dbhelper;
    private LinearLayout baby_health;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment5, null);
    }


    @Override
    public void onStart() {
        super.onStart();
        final LinearLayout update_password=(LinearLayout)getView().findViewById(R.id.update_password);
        /*LinearLayout admin=(LinearLayout)getView().findViewById(R.id.admin);*/
        LinearLayout linearLayout=(LinearLayout)getView().findViewById(R.id.MyInfo);
        final LinearLayout set_up=(LinearLayout)getView().findViewById(R.id.set_up);
        linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context=getActivity();
                Intent intent = new Intent(context, Personal_info.class);
                context.startActivity(intent);
            }
        });
        set_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=getActivity();
                Intent intent = new Intent(context, Set_up.class);
                context.startActivity(intent);
            }
        });
        baby_info=(LinearLayout)getActivity().findViewById(R.id.baby_show_info);
        baby_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),baby_base_info.class);
                startActivity(intent);
            }
        });
        baby_health=(LinearLayout)getActivity().findViewById(R.id.baby_health);
        baby_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Baby_health.class);
                startActivity(intent);
            }
        });
        /*admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),admin_info.class);
                startActivity(intent);
            }
        });*/
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Update_password.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        ImageView tx_info=(ImageView)getView().findViewById(R.id.tx_info);
        TextView fristname=(TextView)getView().findViewById(R.id.fristname);
        TextView autograph=(TextView)getView().findViewById(R.id.autograph);
        String admin=pref.getString("username","");
        dbhelper=new MyDatabasehelper(getActivity(),"User.db",null,3);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from User where username=?",new String[]{admin});
        cursor.moveToFirst();
       String jug=cursor.getString(cursor.getColumnIndex("nickname"));
        String jug1=cursor.getString(cursor.getColumnIndex("autograph"));
        if (cursor.moveToFirst()&&"".equals(jug)){
            fristname.setText("昵称");
        }
        else {

            fristname.setText(cursor.getString(cursor.getColumnIndex("nickname")));
        }
        if (cursor.moveToFirst()&&"".equals(jug1)){
            autograph.setText("个性签名~");
        }
        else
        {
            autograph.setText(cursor.getString(cursor.getColumnIndex("autograph")));
        }
        /**
         * 将数据库中的图片取出
         */
           if(cursor.moveToFirst()&&cursor.getColumnIndex("touxiang")!=0){
               try {
                   byte[] b = cursor.getBlob(cursor.getColumnIndex("touxiang"));
                   Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                   tx_info.setImageBitmap(bitmap);
               }catch (Exception e){
                   e.getMessage();
               }


                    /*else {
                        Drawable drawable=getActivity().getDrawable(R.drawable.tx);
                        BitmapDrawable bd = (BitmapDrawable) drawable;
                        Bitmap bitmap = bd.getBitmap();
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                        tx_info.setImageBitmap(bitmap);

                    }*/
            }

        /**/
       /* cursor.close();*/
    }
}
