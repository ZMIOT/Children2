package BaseAdapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.broadcastbestpractice.R;

import java.util.List;

import Baseclass.Info;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MyInfoAdapter extends ArrayAdapter<Info> {
    private int infoId;

    public MyInfoAdapter(Context context, int textViewId, List<Info> objects) {
        super(context, textViewId, objects);
        infoId = textViewId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Info info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(infoId, parent, false);
        TextView InfoName = (TextView) view.findViewById(R.id.info_name);
        //getContext();
        Log.i("MyInfoAdapter.class", getContext().toString());
        //Log.i("MyInfoAdapter.class",InfoName.getText().toString());
        InfoName.setText(info.getName());
        return view;
    }
}
