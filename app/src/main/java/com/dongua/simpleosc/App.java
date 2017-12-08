package com.dongua.simpleosc;

import android.app.Application;
import android.content.Context;


import com.dongua.simpleosc.db.DaoMaster;
import com.dongua.simpleosc.db.DaoSession;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.greenrobot.greendao.database.Database;

/**
 * Created by dongua on 17-8-6.
 */

public class App extends Application {

    public static final String DB_NAME = "socs";
    private static DaoSession mDaoSession;


    private Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
        AppManager.getInstance().setContext(mContext);
        setupDatabase(this);

        initLog();


    }

    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .tag("WXD")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

    }


    //
    private void setupDatabase(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }
//


    public static DaoSession getDaoSession() {
        return mDaoSession;
    }


}
