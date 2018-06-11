package FragmentTest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.GradeCircle.Comment_detail;
import com.example.GradeCircle.Grade_edit;
import com.example.GradeCircle.album_grade;
import com.example.GradeCircle.comment_grade;
import com.example.GradeCircle.grade_info_detail;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;
import com.facebook.drawee.view.SimpleDraweeView;
/*import com.github.clans.fab.FloatingActionButton;*/
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;

import android.support.design.widget.FloatingActionButton;

/*import BaseAdapterClass.pagerAdapter;*/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import BaseAdapterClass.GradeAdapter;
import Baseclass.EvaluationPic;
import Baseclass.Grade;
import de.hdodenhof.circleimageview.CircleImageView;
import it.neokree.materialtabs.MaterialTabHost;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


/*import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;*/

public class FragmentPage2 extends Fragment{
    MaterialTabHost tabHost;
    ViewPager pager;
    private MyDatabasehelper dbhelper;
    private SimpleDraweeView photo;
    private TextView username;
    private TextView title;
    private TextView time;
    private TextView content;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private  Bitmap  photoBmp;
    private Uri uri;
    private FloatingActionButton grade_info_add;
    private ViewPager viewPager;
    private ViewPager viewPager2;
    private View view1;
    private int[] mImage;
    private Toolbar toolbar;
    private ImmersionBar mImmersionBar;
    private MyDatabasehelper imghelper;
    private SharedPreferences pref;
    private int userflag=0;
    private  GradeAdapter adapter;
    private List<EvaluationPic> imglist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment2,container,false);
        return view;
    }

    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            /*Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);*/
            Glide.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);
        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        NineGridView.setImageLoader(new PicassoImageLoader());

        grade_info_add=(FloatingActionButton)getActivity().findViewById(R.id.grade_info_add);

        final ImageView grade_image_view=(ImageView)getActivity().findViewById(R.id.grade_image_view);

        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerview_grade);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        adapter=new GradeAdapter(R.layout.grade_info_item,buildData());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(adapter);


        Glide.with(getContext()).load(R.mipmap.bg_android).into(grade_image_view);

        pref=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        int userflag= pref.getInt("userflag",0);
        if(userflag==1)
        {
            grade_info_add.setVisibility(View.VISIBLE);
        }
        CircleImageView head_img=(CircleImageView)getActivity().findViewById(R.id.grade_head);
        SharedPreferences pref=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String admin=pref.getString("username","");
        imghelper=new MyDatabasehelper(getContext(),"User.db",null,3);
        SQLiteDatabase db=imghelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from User where username=?",new String[]{admin});
        if(cursor.moveToFirst()&&cursor.getColumnIndex("touxiang")!=0){
            try {
                byte[] b = cursor.getBlob(cursor.getColumnIndex("touxiang"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                head_img.setImageBitmap(bitmap);
            }catch (Exception e){
                e.getMessage();
            }
        }
        grade_info_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Grade_edit.class);
                startActivityForResult(intent,1);
            }
        });



        final FloatingActionButton  comment=(FloatingActionButton)getActivity().findViewById(R.id.comment_btn);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), comment_grade.class);
                startActivity(intent);
            }
        });

        final FloatingActionButton album=(FloatingActionButton)getActivity().findViewById(R.id.album_btn);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), album_grade.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Grade grade=buildData().get(position);
                Intent intent=new Intent(getActivity(),grade_info_detail.class);
                intent.putExtra("title",grade.getTitle());
                intent.putExtra("content",grade.getContent());
                startActivity(intent);
            }
        });
    }

    /**
     * 回调函数先不管
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK)
                {
                   /*String urilist=data.getStringExtra("uris");*/
                   List<String> urilist=data.getStringArrayListExtra("uris");
                    imglist=new ArrayList<>();
                   for(int i=0;i<urilist.size();i++)
                   {
                       EvaluationPic evaluationPic=new EvaluationPic(urilist.get(i),urilist.get(i));
                       imglist.add(evaluationPic);
                   }
                }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

    }





    private List<Grade> buildData() {
        List<Grade> list = new ArrayList<>();
        dbhelper=new MyDatabasehelper(getActivity(),"Grade.db",null,3);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        /*photo=(SimpleDraweeView)getActivity().findViewById(R.id.user_avatar);
        username=(TextView)getActivity().findViewById(R.id.user_name);
        title=(TextView)getActivity().findViewById(R.id.title_01);
        time=(TextView)getActivity().findViewById(R.id.time_xc);
        content=(TextView)getActivity().findViewById(R.id.content_xc);*/

        Cursor cursor=db.rawQuery("select * from Grade",null);
        if(cursor.moveToFirst()){
            do{
                Grade g = new Grade();
                try{
                    /*List<Uri> uris=new ArrayList<>();
                    int count=cursor.getInt(cursor.getColumnIndex("count"));
                    for(int i=0;i<count;i++) {
                        uri = Uri.parse(cursor.getString(cursor.getColumnIndex("uris")));
                        uris.add(uri);
                    }*/
                    uri = Uri.parse(cursor.getString(cursor.getColumnIndex("uris")));
                    String teachername=cursor.getString(cursor.getColumnIndex("teachername"));
                    String title_01=cursor.getString(cursor.getColumnIndex("title"));
                    String time_01=cursor.getString(cursor.getColumnIndex("time"));
                    String content_01=cursor.getString(cursor.getColumnIndex("content"));

                    /* if (uri != null) {
                        photoBmp = getBitmapFormUri(getActivity(), uri);
                    }*/
                    g.Attachments=data();
                    g.UserAvatar=uri;
                    g.Title=title_01;
                    g.time=time_01;
                    g.content=content_01;
                    list.add(g);
                }catch (Exception e){
                    Log.i("ddd","failed");
                }
            }while (cursor.moveToNext());
        }
        else
        {
            cursor.close();
        }
        return list;
    }

    private List<EvaluationPic> data()
    {
        String[] url={"http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png",
         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526738020399&di=cf87338d14d7dd4a919c093b7abcf00d&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140223%2F234786-1402230K95865.jpg"};
        List<EvaluationPic> list=new ArrayList<>();
        for(int i=0;i<url.length;i++)
        {
            EvaluationPic evaluationPic=new EvaluationPic(url[i],url[i]);
            list.add(evaluationPic);
        }
        return list;
    }


    /**
     * 对获取的uri图片进行压缩
     * @param ac
     * @param uri
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
