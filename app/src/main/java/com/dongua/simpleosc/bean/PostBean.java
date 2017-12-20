
package com.dongua.simpleosc.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PostBean implements Parcelable {

    @Id(autoincrement = true)
    private Long dbID;
    @Unique
    private int id;
    private String author;
    private String pubDate;
    private long pubDateLong;
    private int authorid;
    private String portrait;
    private String title;
    private long viewCount;

    private long answerCount;
    private String answerName;
    private String answerTime;


    public PostBean(int id, String author){
        this.id = id;
        this.author = author;
    }

    public PostBean(int id, String author, String pubDate,
                    long pubDateLong, int authorid, String portrait, String title,
                    long viewCount, long answerCount, String answerName,
                    String answerTime) {
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.authorid = authorid;
        this.portrait = portrait;
        this.title = title;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.answerName = answerName;
        this.answerTime = answerTime;
    }



    public String getPubDate() {
        return pubDate;
    }



    public long getPubDateLong() {
        return this.pubDateLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //    private int id;
//    private String author;
//    private String pubDate;
//    private long pubDateLong;
//    private int authorid;
//    private String portrait;
//    private String title;
//    private long viewCount;
//
//    private long answerCount;
//    private String answerName;
//    private String answerTime;
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(author);
        parcel.writeString(pubDate);
        parcel.writeLong(pubDateLong);
        parcel.writeInt(authorid);
        parcel.writeString(portrait);
        parcel.writeString(title);
        parcel.writeLong(viewCount);
        parcel.writeLong(answerCount);
        parcel.writeString(answerName);
        parcel.writeString(answerTime);

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



    public String getAuthor() {
        return this.author;
    }



    public void setAuthor(String author) {
        this.author = author;
    }



    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }



    public void setPubDateLong(long pubDateLong) {
        this.pubDateLong = pubDateLong;
    }



    public int getAuthorid() {
        return this.authorid;
    }



    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }



    public String getPortrait() {
        return this.portrait;
    }



    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }



    public String getTitle() {
        return this.title;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public long getViewCount() {
        return this.viewCount;
    }



    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }



    public long getAnswerCount() {
        return this.answerCount;
    }



    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }



    public String getAnswerName() {
        return this.answerName;
    }



    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }



    public String getAnswerTime() {
        return this.answerTime;
    }



    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }


    public PostBean(Parcel parcel) {
        this.id = parcel.readInt();
        this.author = parcel.readString();
        this.pubDate = parcel.readString();
        this.pubDateLong = parcel.readLong();
        this.title = parcel.readString();
        this.authorid = parcel.readInt();
        this.portrait = parcel.readString();
        this.title = parcel.readString();
        this.viewCount = parcel.readLong();
        this.answerCount = parcel.readLong();
        this.answerName = parcel.readString();
        this.answerTime = parcel.readString();

    }



    @Generated(hash = 627415855)
    public PostBean(Long dbID, int id, String author, String pubDate,
            long pubDateLong, int authorid, String portrait, String title,
            long viewCount, long answerCount, String answerName,
            String answerTime) {
        this.dbID = dbID;
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.authorid = authorid;
        this.portrait = portrait;
        this.title = title;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.answerName = answerName;
        this.answerTime = answerTime;
    }



    @Generated(hash = 1985240350)
    public PostBean() {
    }

    public static final Creator<PostBean> CREATOR = new Creator<PostBean>() {
        @Override
        public PostBean createFromParcel(Parcel parcel) {
            return new PostBean(parcel);
        }

        @Override
        public PostBean[] newArray(int i) {
            return new PostBean[i];
        }
    };


}
