package Baseclass;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.broadcastbestpractice.MyDatabasehelper;

/**
 * Created by Administrator on 2018/4/9.
 */

public class Info {
    private String name;
    private ContentValues values;
    private View view;
    public MyDatabasehelper dbhelper;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * 将个人信息的插入进行封装操作
     *
     * @param activity
     * @param pref
     * @param para
     * @param key
     */
    public void InsertVaules(Context activity, SharedPreferences pref, String para, String key) {
        dbhelper = new MyDatabasehelper(activity, "User.db", null, 2);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String username = pref.getString("username", "");
        Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{username});
        /* String nickName=edit_myName.getText().toString();*/
        if (cursor.moveToFirst() == false) {
            Toast.makeText(activity, "失败", Toast.LENGTH_SHORT).show();
        } else {

            if (cursor != null && cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put(key, para);
                db.update("User", values, "username=?", new String[]{username});
                Toast.makeText(activity, "成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
