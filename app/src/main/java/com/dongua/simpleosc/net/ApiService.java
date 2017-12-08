package com.dongua.simpleosc.net;

import com.dongua.simpleosc.bean.News;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by duoyi on 17-11-22.
 */

public interface ApiService {





    @GET("/action/openapi/token")
    Observable<ResponseBody> getToken(
              @Query("client_id") String id
            , @Query("client_secret") String key
            , @Query("grant_type") String grantType
            , @Query("redirect_uri") String redirect
            , @Query("code") String code
            , @Query("dataType") String dataType);


    @GET("/action/openapi/news_list")
    Observable<List<News>> getNewsList(
            @Query("access_token") String access_token
            , @Query("catalog") int catalog
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("dataType") String dataType);
}
