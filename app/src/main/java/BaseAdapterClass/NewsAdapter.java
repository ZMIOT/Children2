package BaseAdapterClass;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.broadcastbestpractice.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hsy.utils.utilslibrary.utils.GlideUtils;
import com.marshalchen.ultimaterecyclerview.RecyclerItemClickListener;

import java.util.List;

import Baseclass.News;
import Baseclass.PersonCard;

public class NewsAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<PersonCard> mData; //定义数据源
    private OnItemClickListener mOnItemClickListener;

    //定义构造方法，默认传入上下文和数据源
    public NewsAdapter(Context context, List<PersonCard> data) {
        mContext = context;
        mData = data;
    }

    @Override  //将ItemView渲染进来，创建ViewHolder
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, null);
        return new MyViewHolder(view);
    }

    @Override  //将数据源的数据绑定到相应控件上
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder holder2 = (MyViewHolder) holder;
        PersonCard personCard = mData.get(position);
        Uri uri = Uri.parse(personCard.avatarUrl);
        /*holder2.userAvatar.setImageURI(uri);*/
        Glide.with(mContext).load(uri).into(holder2.userAvatar);
        holder2.userAvatar.getLayoutParams().height = personCard.imgHeight; //从数据源中获取图片高度，动态设置到控件上
        holder2.userName.setText(personCard.name);
        holder2.content.setText(personCard.content);
        holder2.title.setText(personCard.title);
        holder2.time.setText(personCard.time);
        holder2.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int pos=holder2.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    //定义自己的ViewHolder，将View的控件引用在成员变量上
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public SimpleDraweeView userAvatar;
        public TextView userName;
        public TextView content;
        public TextView title;
        public TextView time;
        private OnItemClickListener mListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            userAvatar = (SimpleDraweeView) itemView.findViewById(R.id.user_avatar);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            content=(TextView)itemView.findViewById(R.id.content_xc);
            title=(TextView)itemView.findViewById(R.id.title_01);
            time=(TextView)itemView.findViewById(R.id.time_xc);
        }

        //实现OnClickListener接口
        @Override
        public void onClick(View view) {
            if(mListener != null){

            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int postion);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
}
