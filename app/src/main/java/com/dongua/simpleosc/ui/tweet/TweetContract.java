package com.dongua.simpleosc.ui.tweet;

import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.bean.TweetBean;
import com.dongua.simpleosc.ui.news.NewsContract;

import java.util.List;

/**
 * author: Lewis
 * date: On 18-1-23.
 */

public class TweetContract {
    interface View extends BaseView {
        void requestFinished(List<TweetBean> data);

        void requestFailed();
    }


    interface Presenter extends BasePresenter<TweetContract.View> {
        void requestAll(int type);

        void requestBefore(String pubDate, int type);

        void cancelRequest();

        void loadMore();

    }

    interface Model {

        void cacheData(List<TweetBean> data, int type);

        void setRequestListener(TweetContract.OnRequestListener listener);

        void cancelRequest();

        void getData(int type, String pubDate);

    }

    interface OnRequestListener {
        void successed(List<TweetBean> bean);

        void failed();
    }
}
