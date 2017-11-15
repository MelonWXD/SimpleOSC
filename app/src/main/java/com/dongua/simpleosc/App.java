package com.dongua.simpleosc;

import android.app.Application;
import android.content.Context;


import org.greenrobot.greendao.database.Database;

/**
 * Created by dongua on 17-8-6.
 */

public class App extends Application {

//    private static DaoSession mDaoSession;


    private Context mContext;



    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
        AppManager.getInstance().setContext(mContext);
//        setupDatabase(this);




    }




//
//    private void setupDatabase(Context context) {
//        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DATABASE_WEATHER);
//        Database db = openHelper.getWritableDb();
//        DaoMaster daoMaster = new DaoMaster(db);
//        mDaoSession = daoMaster.newSession();
//    }
//

//
//    public static DaoSession getDaoSession() {
//        return mDaoSession;
//    }





}
