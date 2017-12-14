package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.bean.SubBean;

import java.util.List;

/**
 * Created by duoyi on 17-12-8.
 */

public interface SubContract {
    interface View extends BaseView{
//        void updateRecyclerView(News data);
        void requestFinished(List<SubBean> data);
//        void requestFinished2(List<News> data);

        void requestFailed();
    }


    interface Presenter extends BasePresenter<View>{
        void requestAll(int type);
        void requestBefore(String pubDate, int type);
        void cancelRequest();

        void loadMore();

    }

    interface Model {

        void cacheData(List<SubBean> data);
        void setRequestListener(OnRequestListener listener);
        void cancelRequest();

        void getData(int type,String pubDate);

//        void getNewsBefore(String pubDate);
    }

    interface OnRequestListener{
        void successed(List<SubBean> bean);
        void failed();
    }
}
