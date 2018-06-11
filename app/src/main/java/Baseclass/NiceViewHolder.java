package Baseclass;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.NineGridView;

public class NiceViewHolder extends BaseViewHolder{
    public NineGridView nineGrid;

    public NiceViewHolder(View view, NineGridView nineGrid) {
        super(view);
        this.nineGrid = nineGrid;
    }
}
