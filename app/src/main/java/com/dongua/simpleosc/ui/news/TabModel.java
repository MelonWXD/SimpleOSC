package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.bean.News;
import com.dongua.simpleosc.net.RetrofitClient;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by duoyi on 17-12-8.
 */

public class TabModel implements NewsContract.Model {

    private NewsContract.OnRequestListener mListener;

    @Override
    public void getNews() {
        RetrofitClient.getInstance().getNewsList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.i("news",d);
                    }

                    @Override
                    public void onNext(List<News> news) {
                        Logger.i("news",news);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i("news"+e.getCause());

                    }

                    @Override
                    public void onComplete() {
                        Logger.i("news");

                    }
                });
    }


    @Override
    public void cacheData() {

    }

    @Override
    public void setRequestListener(NewsContract.OnRequestListener listener) {
        mListener = listener;
    }


}
