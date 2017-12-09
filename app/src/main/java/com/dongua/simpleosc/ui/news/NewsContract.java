package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.bean.News;

import java.util.List;

/**
 * Created by duoyi on 17-12-8.
 */

public interface NewsContract  {
    interface View extends BaseView{
//        void updateRecyclerView(News data);
        void requestFinished(List<News> data);

        void requestFailed();
    }


    interface Presenter extends BasePresenter{
        void requestAllNews();
        void requestNewsBefore(String pubDate);
    }

    interface Model{
        void getNews(String pubDate);
        void cacheData();
        void setRequestListener(OnRequestListener listener);
//        void getNewsBefore(String pubDate);
    }

    interface OnRequestListener<T>{
        void successed(T bean);
        void failed();
    }
}
