package com.dongua.simpleosc.bean;

import java.io.Serializable;
import java.util.List;


import com.dongua.simpleosc.db.StringConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DetailUserBean implements Serializable {
    private static final long serialVersionUID = 1L;


    public static final int MALE = 1;
    public static final int FEMALE = 2;
    @Id(autoincrement = true)
    private Long dbID;
    @Unique
    private int uid;
    public String name;
    public int gender;
    public String portrait;

    public String city;
    public String province;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> expertise;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> platforms;

    public String joinTime;
    public String lastLoginTime;

    public int favoriteCount;
    public int followersCount;
    public int fansCount;

    public DetailUserBean(int uid, String name, int gender,
                          String portrait, String city, String province, List<String> expertise,
                          List<String> platforms, String joinTime, String lastLoginTime,
                          int favoriteCount, int followersCount, int fansCount) {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.portrait = portrait;
        this.city = city;
        this.province = province;
        this.expertise = expertise;
        this.platforms = platforms;
        this.joinTime = joinTime;
        this.lastLoginTime = lastLoginTime;
        this.favoriteCount = favoriteCount;
        this.followersCount = followersCount;
        this.fansCount = fansCount;
    }

    @Generated(hash = 1351063905)
    public DetailUserBean(Long dbID, int uid, String name, int gender,
                          String portrait, String city, String province, List<String> expertise,
                          List<String> platforms, String joinTime, String lastLoginTime,
                          int favoriteCount, int followersCount, int fansCount) {
        this.dbID = dbID;
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.portrait = portrait;
        this.city = city;
        this.province = province;
        this.expertise = expertise;
        this.platforms = platforms;
        this.joinTime = joinTime;
        this.lastLoginTime = lastLoginTime;
        this.favoriteCount = favoriteCount;
        this.followersCount = followersCount;
        this.fansCount = fansCount;
    }

    @Generated(hash = 1235622456)
    public DetailUserBean() {
    }

    public Long getDbID() {
        return this.dbID;
    }

    public void setDbID(Long dbID) {
        this.dbID = dbID;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPortrait() {
        return this.portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<String> getExpertise() {
        return this.expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public List<String> getPlatforms() {
        return this.platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public String getJoinTime() {
        return this.joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getFavoriteCount() {
        return this.favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getFollowersCount() {
        return this.followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFansCount() {
        return this.fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }


}