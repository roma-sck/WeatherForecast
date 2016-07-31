package kultprosvet.com.wheatherforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
    public static DaoSession getSession(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cities_db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}
