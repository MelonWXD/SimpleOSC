package com.dongua.simpleosc.ui.news;

import android.database.sqlite.SQLiteConstraintException;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.bean.PostBean;
import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.db.PostBeanDao;
import com.dongua.simpleosc.db.SubBeanDao;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.bean.NewsTab.TYPE_BLOG;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_DAILY;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_NEWS;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_POST;
import static com.dongua.simpleosc.utils.Util.str2Date;

/**
 * Created by duoyi on 17-12-20.
 */

public class NewsModel<T> implements NewsContract.Model<T> {

    private NewsContract.OnRequestListener<T> mListener;
    private Disposable mDisposable;

    @Override
    public void setRequestListener(NewsContract.OnRequestListener<T> listener) {
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
                n.setPubDateLong(str2Date(n.getPubDate()));
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
                n.setPubDateLong(str2Date(n.getPubDate()));
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


    private void requestPost(final String pubDate) {
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

                            if (pubDate != null && pubDate.isEmpty()) {

                                Iterator<PostBean> iterator = data.iterator();
                                while (iterator.hasNext()) {
                                    if (!Util.dateCompare(iterator.next().getPubDate(), pubDate))
                                        iterator.remove();
                                }
                            }

                            cacheData((List<T>) data);
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
        List<PostBean> data = new ArrayList<>();
        JsonObject joo = Util.string2Json(rep);
        JsonArray ja = joo.get("post_list").getAsJsonArray();
//        JsonArray ja = Util.string2JsonArray(rep);
        for (int i = 0; i < ja.size(); i++) {
            JsonObject jo = ja.get(i).getAsJsonObject();
            String author = jo.get("author").getAsString();
            int id = jo.get("id").getAsInt();
            int viewCount = jo.get("viewCount").getAsInt();
            String title = jo.get("title").getAsString();
            String portrait = jo.get("portrait").getAsString();
            int authorid = jo.get("authorid").getAsInt();
            String pubDate = jo.get("pubDate").getAsString();

            int answerCount = jo.get("answerCount").getAsInt();
            JsonElement answer = jo.get("answer");
            String answerName = "";
            String answerTime = "";
            if (answer instanceof JsonObject) {
                answerName = answer.getAsJsonObject().get("name").getAsString();
                answerTime = answer.getAsJsonObject().get("time").getAsString();
            }
            data.add(new PostBean(id, author, pubDate, str2Date(pubDate), authorid, portrait, title, viewCount, answerCount, answerName, answerTime));
        }

        return data;
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
//                        if (pubDate == null || pubDate.isEmpty()) {
//                            mListener.successed((List<T>) news);
//                            cacheData((List<T>) news);
//                        } else {
//                            List<SubBean> update = new ArrayList<>();
//                            for (SubBean n : news) {
//                                if (Util.dateCompare(n.getPubDate(), pubDate)) {
//                                    update.add(n);
//                                }
//                            }
//
//                            cacheData((List<T>) update);
//
//                            mListener.successed((List<T>) update);
//
//                        }

                        if (pubDate != null && !pubDate.isEmpty()) {
                            Iterator<SubBean> iterator = news.iterator();
                            while (iterator.hasNext()) {
                                if (!Util.dateCompare(iterator.next().getPubDate(), pubDate))
                                    iterator.remove();
                            }
                        }

                        mListener.successed((List<T>) news);
                        cacheData((List<T>) news);
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
//                        if (pubDate == null || pubDate.isEmpty()) {
//                            mListener.successed((List<T>) blogs);
//                            cacheData((List<T>) blogs);
//
//                        } else {
//                            List<SubBean> update = new ArrayList<>();
//                            for (SubBean b : blogs) {
//                                if (Util.dateCompare(b.getPubDate(), pubDate)) {
//                                    update.add(b);
//                                }
//                            }
//                            mListener.successed((List<T>) update);
//                            cacheData((List<T>) update);
//                            Logger.d("数据是否为空: " + update.isEmpty());
//
//                        }


                        if (pubDate != null && !pubDate.isEmpty()) {
                            Iterator<SubBean> iterator = blogs.iterator();
                            while (iterator.hasNext()) {
                                if (!Util.dateCompare(iterator.next().getPubDate(), pubDate))
                                    iterator.remove();
                            }
                        }

                        mListener.successed((List<T>) blogs);
                        cacheData((List<T>) blogs);
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
