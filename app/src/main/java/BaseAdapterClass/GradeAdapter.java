package BaseAdapterClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.broadcastbestpractice.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.apache.http.client.fluent.Content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Baseclass.EvaluationPic;
import Baseclass.Grade;
import Baseclass.gradeViewHolder;



public class GradeAdapter extends BaseQuickAdapter <Grade,gradeViewHolder>{

private List<Grade> mdata;
    public GradeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        this.mdata=data;
    }
    @Override
    protected void convert(gradeViewHolder helper, Grade item) {
        helper.setText(R.id.title_01_grade,item.getTitle());
        helper.setText(R.id.content_grade,item.getContent());
        helper.setText(R.id.time_grade,item.getTime());
        /*helper.setImageBitmap(R.id.user_avatar_grade,item.);*/
        Glide.with(mContext).load(item.getUserAvatar()).into(helper.getPhoto());
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<EvaluationPic> imageDetails = item.getAttachments();
        if (imageDetails != null) {
            for (EvaluationPic imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail.smallImageUrl);
                info.setBigImageUrl(imageDetail.imageUrl);
                imageInfo.add(info);
            }
        }
        helper.nineGrid.setAdapter(new NineGridViewClickAdapter(mContext,imageInfo));
    }
}

