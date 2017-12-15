package com.dongua.simpleosc.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import static com.dongua.simpleosc.utils.Util.str2Date;

@Entity
public class SubBean implements Parcelable{

    @Id(autoincrement = true)
    private Long dbID;
    @Unique
    private int id;
    private String author;
    private String pubDate;
    private long pubDateLong;
    private String title;
    private int authorid;
    private int commentCount;
    private int type;


    public SubBean(int id, String author, String pubDate, String title,
                   int authorid, int commentCount, int type) {
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = str2Date(pubDate);
        this.title = title;
        this.authorid = authorid;
        this.commentCount = commentCount;
        this.type = type;
    }

    public SubBean(int id, String author, String pubDate, String title,long pubDateLong,
                   int authorid, int commentCount, int type) {
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.title = title;
        this.authorid = authorid;
        this.commentCount = commentCount;
        this.type = type;
    }


    @Generated(hash = 432093772)
    public SubBean(Long dbID, int id, String author, String pubDate,
            long pubDateLong, String title, int authorid, int commentCount,
            int type) {
        this.dbID = dbID;
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.title = title;
        this.authorid = authorid;
        this.commentCount = commentCount;
        this.type = type;
    }


    @Generated(hash = 274721787)
    public SubBean() {
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


    public String getPubDate() {
        return this.pubDate;
    }


    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


    public long getPubDateLong() {
        return this.pubDateLong;
    }


    public void setPubDateLong(long pubDateLong) {
        this.pubDateLong = pubDateLong;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getAuthorid() {
        return this.authorid;
    }


    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }


    public int getCommentCount() {
        return this.commentCount;
    }


    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


    public int getType() {
        return this.type;
    }


    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }
//    private int id;
//    private String author;
//    private String pubDate;
//    private long pubDateLong;
//    private String title;
//    private int authorid;
//    private int commentCount;
//    private int type;
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(author);
        parcel.writeString(pubDate);
        parcel.writeLong(pubDateLong);
        parcel.writeString(title);
        parcel.writeInt(authorid);
        parcel.writeInt(commentCount);
        parcel.writeInt(type);

    }


    public SubBean(Parcel parcel) {
        this.id = parcel.readInt();
        this.author = parcel.readString();
        this.pubDate = parcel.readString();
        this.pubDateLong = parcel.readLong();
        this.title = parcel.readString();
        this.authorid = parcel.readInt();
        this.commentCount = parcel.readInt();
        this.type = parcel.readInt();
    }

    public static final Creator<SubBean> CREATOR = new Creator<SubBean>() {
        @Override
        public SubBean createFromParcel(Parcel parcel) {
            return new SubBean(parcel);
        }

        @Override
        public SubBean[] newArray(int i) {
            return new SubBean[i];
        }
    };


}
