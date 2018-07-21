package BaseAdapterClass;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.broadcastbestpractice.R;

import java.util.List;

import Baseclass.Baby;

public class BabyAdapter extends BaseQuickAdapter<Baby, BaseViewHolder> {
    private List<Baby> mList;

    public BabyAdapter(int layoutResId, @Nullable List<Baby> data) {
        super(layoutResId, data);
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Baby item) {
        helper.setText(R.id.name_baby, item.getBabyname());
        helper.setText(R.id.phone_parent, item.getParent_phone());
    }
}
