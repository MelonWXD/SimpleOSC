package com.dongua.simpleosc.ui.news;

import android.database.sqlite.SQLiteConstraintException;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.bean.PostBean;
import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.db.PostBeanDao;
import com.dongua.simpleosc.db.SubBeanDao;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.Util;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.bean.NewsTab.TYPE_BLOG;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_DAILY;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_NEWS;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_POST;

/**
 * Created by duoyi on 17-12-20.
 */

public class BaseModel<T> implements SubContract.Model<T> {

    private SubContract.OnRequestListener<T> mListener;
    private Disposable mDisposable;

    @Override
    public void setRequestListener(SubContract.OnRequestListener<T> listener) {
        mListener = listener;
    }

    @Override
    public void cancelRequest() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public void cacheData(@NotNull List<T> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        if (data.get(0) instanceof SubBean) {
            SubBeanDao dao = App.getDaoSession().getSubBeanDao();

            for (int i = 0; i < data.size(); i++) {
                SubBean n = (SubBean) data.get(i);
                n.setPubDateLong(Util.str2Date(n.getPubDate()));
                try {
                    dao.save(n);
                } catch (SQLiteConstraintException exception) {
                    Logger.e(exception.getMessage());
                }
            }
        } else if (data.get(0) instanceof PostBean) {
            PostBeanDao dao = App.getDaoSession().getPostBeanDao();

            for (int i = 0; i < data.size(); i++) {
                PostBean n = (PostBean) data.get(i);
                n.setPubDateLong(Util.str2Date(n.getPubDate()));
                try {
                    dao.save(n);
                } catch (SQLiteConstraintException exception) {
                    Logger.e(exception.getMessage());
                }
            }
        }

    }


    //todo baseObserver 负责完成请求 回调更新 变量List在onNExt里保存 onCompete中调用监听器
    @Override
    public void getData(int type, String pubDate) {
        switch (type) {
            case TYPE_NEWS:
                Logger.d("request News");
                requestNews(pubDate);
                break;
            case TYPE_BLOG:
                Logger.d("request Blog");
                requestBlog(pubDate);
                break;
            case TYPE_DAILY:
                break;
            case TYPE_POST:
                Logger.d("request Post");
                requestPost(pubDate);
                break;
            default:
        }

    }


    private void requestPost(String pubDate) {
        RetrofitClient.getInstance().getPostList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            List<PostBean> data = parsePostJson(responseBody.string());
                            mListener.successed((List<T>) data);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.failed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private List<PostBean> parsePostJson(String rep) {
        PostBean pb = new PostBean(1, "donggua");
        pb.setTitle("ahahahahah");
        List<PostBean> ret = new ArrayList();
        ret.add(pb);
        return ret;
    }


    private void requestNews(final String pubDate) {
        RetrofitClient.getInstance().getNewsList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<SubBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }


                    @Override
                    public void onNext(List<SubBean> news) {
                        if (pubDate == null || pubDate.isEmpty()) {
                            mListener.successed((List<T>) news);
                            cacheData((List<T>) news);
                        } else {
                            List<SubBean> update = new ArrayList<>();
                            for (SubBean n : news) {
                                if (Util.dateCompare(n.getPubDate(), pubDate)) {
                                    update.add(n);
                                }
                            }

                            cacheData((List<T>) update);

                            mListener.successed((List<T>) update);
                            Logger.d("新闻数据为空: " + update.isEmpty());

                        }
//                        Logger.d(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.failed();
                    }

                    @Override
                    public void onComplete() {
//                        Logger.d("onComplete");

                    }
                });
    }

    private void requestBlog(final String pubDate) {

        RetrofitClient.getInstance().getBlogList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<SubBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<SubBean> blogs) {
                        if (pubDate == null || pubDate.isEmpty()) {
                            mListener.successed((List<T>) blogs);
                            cacheData((List<T>) blogs);

                        } else {
                            List<SubBean> update = new ArrayList<>();
                            for (SubBean b : blogs) {
                                if (Util.dateCompare(b.getPubDate(), pubDate)) {
                                    update.add(b);
                                }
                            }
                            mListener.successed((List<T>) update);
                            cacheData((List<T>) update);
                            Logger.d("数据是否为空: " + update.isEmpty());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.failed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
