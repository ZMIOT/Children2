package FragmentTest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.GradeCircle.improve_acc_infomation;
import com.example.administrator.broadcastbestpractice.MyDatabasehelper;
import com.example.administrator.broadcastbestpractice.R;



import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/4/6.
 */


public class FragmentPage1 extends Fragment {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private MyDatabasehelper dbhelper;
    private int state=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button add_nursery=(Button)getView().findViewById(R.id.addToNursery);
        add_nursery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context activity=getActivity();
                Intent intent=new Intent(activity,improve_acc_infomation.class);
                startActivity(intent);
            }
        });
    }
}
