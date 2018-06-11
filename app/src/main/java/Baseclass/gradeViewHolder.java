package Baseclass;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.broadcastbestpractice.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.ninegrid.NineGridView;

public class gradeViewHolder extends BaseViewHolder {
    public ImageView photo;
    public NineGridView nineGrid;
    public TextView username;
    public TextView title;
    public TextView time;
    public TextView content;
    public gradeViewHolder(View view)
    {
        super(view);
        nineGrid=(NineGridView)view.findViewById(R.id.nineGrid);
        username=(TextView)view.findViewById(R.id.user_grade);
        title=(TextView)view.findViewById(R.id.title_01_grade);
        time=(TextView)view.findViewById(R.id.time_grade);
        content=(TextView)view.findViewById(R.id.content_grade);
        photo=(ImageView)view.findViewById(R.id.user_avatar_grade);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }


    public gradeViewHolder setUserAvatar( int viewId,  String imageResId) {
        return this;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(TextView username) {
        this.username = username;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }
}
