package com.dongua.simpleosc.net;

import com.dongua.simpleosc.bean.SubBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Leiws on 17-11-22.
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

    @GET("/action/openapi/token")
    Observable<ResponseBody> refreshToken(
            @Query("client_id") String id
            , @Query("client_secret") String key
            , @Query("grant_type") String grantType
            , @Query("redirect_uri") String redirect
            , @Query("refresh_token") String refreshToken
            , @Query("dataType") String dataType);


    @GET("/action/openapi/news_list")
    Observable<List<SubBean>> getNewsList(
            @Query("access_token") String access_token
            , @Query("catalog") int catalog
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("dataType") String dataType);

    @GET("/action/openapi/news_detail")
    Observable<ResponseBody> getNewsDetail(
            @Query("id") int id
            , @Query("access_token") String mAccessToken
            , @Query("dataType") String dataType);


    @GET("/action/openapi/blog_recommend_list")
    Observable<List<SubBean>> getBlogList(
            @Query("access_token") String access_token
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("dataType") String dataType);

    @GET("/action/openapi/blog_detail")
    Observable<ResponseBody> getBlogDetail(
            @Query("id") int id
            , @Query("access_token") String mAccessToken
            , @Query("dataType") String dataType);


    @GET("/action/openapi/post_list")
    Observable<ResponseBody> getPostList(
            @Query("access_token") String access_token
            , @Query("catalog") int catalog
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("dataType") String dataType);

    @GET("/action/openapi/post_list")
    Observable<ResponseBody> getPostListByTag(
            @Query("access_token") String access_token
            , @Query("catalog") int catalog
            , @Query("tag") String tag
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("dataType") String dataType);

    @GET("/action/openapi/post_detail")
    Observable<ResponseBody> getPostDetail(
            @Query("id") int id
            , @Query("access_token") String mAccessToken
            , @Query("dataType") String dataType);

    @GET("/action/openapi/tweet_list")
    Observable<ResponseBody> getTweetList(
            @Query("access_token") String access_token
            , @Query("user") long userID
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("dataType") String dataType);

    @GET("/action/openapi/post_detail")
    Observable<ResponseBody> getTweetDetail(
            @Query("id") int id
            , @Query("access_token") String mAccessToken
            , @Query("dataType") String dataType);

    @GET("/action/openapi/user")
    Observable<ResponseBody> getMyInfo(
            @Query("access_token") String access_token
            , @Query("dataType") String dataType);

    @GET("/action/openapi/my_information")
    Observable<ResponseBody> getMyDetailInfo(
            @Query("access_token") String access_token
            , @Query("dataType") String dataType);

    @GET("/action/openapi/user_information")
    Observable<ResponseBody> getUserInfo(
            @Query("access_token") String access_token
            , @Query("access_token") String user
            , @Query("access_token") String friend
            , @Query("dataType") String dataType);



}
