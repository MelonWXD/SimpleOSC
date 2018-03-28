package com.dongua.simpleosc.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: Lewis
 * date: On 18-1-25.
 */
@Entity
public class UserBean {

    //"gender":"female",
//        "name":"你若成风归去",
//        "location":"null null",
//        "id":2969065,
//        "avatar":"https://www.oschina.net/img/portrait.gif",
//        "email":"549295838@qq.com",
//        "url":"https://my.oschina.net/pxmwxd"
    @Id(autoincrement = true)
    private Long dbID;
    @Unique
    private int id;
    private String name;
    private String gender;
    private String location;
    private String avatar;
    private String email;
    private String url;

    private int tweetCnt;
    private int favoriteCNt;
    private int followingCnt;
    private int followerCnt;


    public UserBean(int id, String name, String gender, String location,
                    String avatar, String email, String url) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.location = location;
        this.avatar = avatar;
        this.email = email;
        this.url = url;
    }
    @Generated(hash = 918997659)
    public UserBean(Long dbID, int id, String name, String gender, String location,
            String avatar, String email, String url, int tweetCnt, int favoriteCNt,
            int followingCnt, int followerCnt) {
        this.dbID = dbID;
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.location = location;
        this.avatar = avatar;
        this.email = email;
        this.url = url;
        this.tweetCnt = tweetCnt;
        this.favoriteCNt = favoriteCNt;
        this.followingCnt = followingCnt;
        this.followerCnt = followerCnt;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getDbID() {
        return this.dbID;
    }
    public void setDbID(Long dbID) {
        this.dbID = dbID;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getTweetCnt() {
        return this.tweetCnt;
    }
    public void setTweetCnt(int tweetCnt) {
        this.tweetCnt = tweetCnt;
    }
    public int getFavoriteCNt() {
        return this.favoriteCNt;
    }
    public void setFavoriteCNt(int favoriteCNt) {
        this.favoriteCNt = favoriteCNt;
    }
    public int getFollowingCnt() {
        return this.followingCnt;
    }
    public void setFollowingCnt(int followingCnt) {
        this.followingCnt = followingCnt;
    }
    public int getFollowerCnt() {
        return this.followerCnt;
    }
    public void setFollowerCnt(int followerCnt) {
        this.followerCnt = followerCnt;
    }


}



