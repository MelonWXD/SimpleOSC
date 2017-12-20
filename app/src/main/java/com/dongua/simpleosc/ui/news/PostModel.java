package com.dongua.simpleosc.ui.news;

import android.database.sqlite.SQLiteConstraintException;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.bean.PostBean;
import com.dongua.simpleosc.db.PostBeanDao;
import com.dongua.simpleosc.utils.Util;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by duoyi on 17-12-20.
 */

public class PostModel extends BaseModel<PostBean> {

    @Override
    public void cacheData(List<PostBean> data) {
        PostBeanDao dao = App.getDaoSession().getPostBeanDao();

        for (PostBean n : data) {
            n.setPubDateLong(Util.str2Date(n.getPubDate()));
            try {
                dao.save(n);
            } catch (SQLiteConstraintException exception) {
                Logger.e(exception.getMessage());
            }
        }
    }
}
