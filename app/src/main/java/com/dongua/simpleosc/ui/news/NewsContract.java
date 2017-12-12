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


    interface Presenter extends BasePresenter<View>{
        void requestAllNews();
        void requestNewsBefore(String pubDate);
        void cancelRequest();

        void loadMore();
    }

    interface Model<T>{
        void getNews(String pubDate);
        void cacheData(T data);
        void setRequestListener(OnRequestListener<T> listener);
        void cancelRequest();

        void loadMore();

//        void getNewsBefore(String pubDate);
    }

    interface OnRequestListener<T>{
        void successed(T bean);
        void failed();
    }
}
