package BaseAdapterClass;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.broadcastbestpractice.R;

import java.util.List;

import Baseclass.Teacher;

public class TeacherAdapter extends BaseQuickAdapter <Teacher,BaseViewHolder>{
    private View view;

    public TeacherAdapter(int layoutResId, @Nullable List<Teacher> data, View view) {
        super(layoutResId, data);
        this.view = view;
    }

    @Override
    protected void convert(BaseViewHolder helper, Teacher item) {
        /*ImageView head_teacher=view.findViewById(R.id.head_teacher);*/
        helper.setText(R.id.name_teacher,item.getName());
       /* helper.setText(R.id.phone_teacher,item.getPhone());*/

       helper.setText(R.id.phone_teacher,item.getPhone());
       /* if(item.getImage()!=null){
            Glide.with(mContext).load(item.getImage()).into(head_teacher);
        }
        else
        {
            Glide.with(mContext).load(R.mipmap.head_img).into(head_teacher);
        }*/
    }
}
