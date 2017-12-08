package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.bean.News;

/**
 * Created by duoyi on 17-12-8.
 */

public interface NewsContract  {
    interface View extends BaseView{
        void updateRecyclerView(News data);
    }


    interface Presenter extends BasePresenter{
        void requestAllNews();
    }

    interface Model{
        void getNews();
        void cacheData();
        void setRequestListener(OnRequestListener listener);
    }

    interface OnRequestListener<T>{
        void successed(T bean);
    }
}
