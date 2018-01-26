package com.dongua.simpleosc.net;

import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.net.convert.CustomConverterFactory;
import com.dongua.simpleosc.net.interceptor.LoggingInterceptor;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;


import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.OkHttpClient;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import android.support.v4.util.ArrayMap;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.REFRESH_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.TOKEN_EXPIRE;


/**
 * Created by duoyi on 17-11-22.
 */

public class RetrofitClient {

    public static final String BASE_URL = "http://www.oschina.net";

    public static final String APP_ID = "ZQhp2vSqj0m17HfGyS0y";
    public static final String APP_KEY = "5lGVEzMexPFyLQiyN7P0af99yeplIvyj";
    public static final String GRANT_TYPE_AUTH = "authorization_code";
    public static final String GRANT_TYPE_REFRESH = "refresh_token";
    public static final String DATA_TYPE = "json";
    public static final String REDIRECT_URL = "http://chun0dong-001-site1.site4future.com/";

    public static final int DEFAULT_CATALOG = 1;
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGESIZE = 20;

    //类别ID 1-问答 2-分享 3-IT杂烩(综合) 4-站务 100-职业生涯 0-所有
    public static final int DEFAULT_POST_CATALOG = 1;


    private ArrayMap<String, Observer> requestMap;

    private String mAccessToken;

    private static RetrofitClient mInstance;

    private static OkHttpClient mHttpClient;
    private static Retrofit mRetrofit;

    private ApiService mApi;


    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }


    private RetrofitClient() {

        requestMap = new ArrayMap<>();

        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
//                .addInterceptor(new SaveCookiesInterceptor())
//                .addInterceptor(new SetCookiesInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build();

        mApi = mRetrofit.create(ApiService.class);
    }

    public void setAccessToken(String mAccessToken) {
        this.mAccessToken = mAccessToken;
    }


    public Observable<ResponseBody> getToken(String code) {
        return mApi.getToken(APP_ID, APP_KEY, GRANT_TYPE_AUTH, REDIRECT_URL, code, DATA_TYPE);
    }

    public Observable<ResponseBody> refreshToken() {
        return mApi.refreshToken(APP_ID, APP_KEY, GRANT_TYPE_REFRESH, REDIRECT_URL, (String) SharedPreferenceUtil.get(REFRESH_TOKEN, ""), DATA_TYPE);
    }

    public Observable<List<SubBean>> getNewsList() {
        if (mAccessToken == null) {
            return null;
        }
        return mApi.getNewsList(mAccessToken, DEFAULT_CATALOG, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<List<SubBean>> getNewsList(String access_code) {
        return mApi.getNewsList(access_code, DEFAULT_CATALOG, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getNewsDetail(int id) {
        return mApi.getNewsDetail(id, mAccessToken, DATA_TYPE);
    }


    public Observable<List<SubBean>> getBlogList(String access_code) {
        return mApi.getBlogList(access_code, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getBlogDetail(int id) {
        return mApi.getBlogDetail(id, mAccessToken, DATA_TYPE);
    }

    public Observable<List<SubBean>> getBlogList() {
        return mApi.getBlogList(mAccessToken, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getPostList() {
        return mApi.getPostList(mAccessToken, DEFAULT_POST_CATALOG, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }


    public Observable<ResponseBody> getPostList(int catalog) {
        return mApi.getPostList(mAccessToken, catalog, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getPostListByTag(String tag) {
        return mApi.getPostListByTag(mAccessToken, DEFAULT_POST_CATALOG, tag, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getPostListByTag(String tag, int catalog) {
        return mApi.getPostListByTag(mAccessToken, catalog, tag, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getPostDetail(int id) {
        return mApi.getPostDetail(id, mAccessToken, DATA_TYPE);
    }


    public Observable<ResponseBody> getTweetList(long userID) {
        return mApi.getTweetList(mAccessToken, userID, DEFAULT_PAGE, DEFAULT_PAGESIZE, DATA_TYPE);
    }

    public Observable<ResponseBody> getUserInfo() {
        return mApi.getUserInfo(mAccessToken, DATA_TYPE);
    }


    public void add(String key, Observer request) {
        requestMap.put(key, request);
    }

    public void cancel(String key) {
        requestMap.remove(key);
    }

    public void cancelAll() {
        requestMap.clear();
    }

    public boolean isAccessExpire() {
        long timeStamp = (Long) SharedPreferenceUtil.get(TOKEN_EXPIRE, 0L);
        long now = new Date().getTime();
        return timeStamp < now;
    }


//    public boolean isRefreshInvalid(String refreshToken) {
//    }
}
