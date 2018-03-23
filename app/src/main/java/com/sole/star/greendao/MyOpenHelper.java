package com.sole.star.greendao;

import android.content.Context;
import android.util.Log;

import com.sole.star.greendao.gen.DaoMaster;
import com.sole.star.greendao.gen.DaoSession;
import com.sole.star.greendao.gen.UserDao;

import org.greenrobot.greendao.database.Database;

public class MyOpenHelper extends DaoMaster.OpenHelper {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static final String DBNAME = "greendao.db";

    public MyOpenHelper(Context context) {
        super(context, DBNAME, null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion) {
            Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.getInstance().migrate(db, UserDao.class);
            //更改过的实体类(新增的不用加) 更新UserDao文件 可以添加多个XXDao.class 文件
            // MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    DBNAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}