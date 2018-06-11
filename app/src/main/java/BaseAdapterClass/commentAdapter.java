package BaseAdapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.broadcastbestpractice.R;

import java.util.List;

import Baseclass.Comment;
import Baseclass.Grade;

public class commentAdapter  extends BaseQuickAdapter<Comment,BaseViewHolder> {
    private List<Comment> mdata;
    public commentAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        this.mdata=data;
    }
    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.baby_name,item.getBabyname());
    }


    public void refresh(List<Comment> mList)
    {
        mdata=mList;
        notifyDataSetChanged();
    }
}
