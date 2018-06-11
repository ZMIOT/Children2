package utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.broadcastbestpractice.MyDatabasehelper;

public class DataStorageUtils {
    private  SharedPreferences pref;
    private  SharedPreferences.Editor editor;
    private  MyDatabasehelper dbhelper;

    /**
     * 將創建數據庫對象封裝起來
     * @param dbhelper
     * @param context
     * @param databaseName
     * @return
     */
    private  MyDatabasehelper createDbObj(MyDatabasehelper dbhelper,Context context,String databaseName){
        dbhelper=new MyDatabasehelper(context,databaseName,null,3);
        return dbhelper;
    }
}
