package com.dongua.simpleosc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: Lewis
 * date: On 18-1-22.
 */
@Entity
public class TweetBean implements Parcelable{
    @Id(autoincrement = true)
    private Long dbID;
    @Unique
    private int id;
    private String author;
    private String pubDate;
    private long pubDateLong;
    private int authorid;
    private String portrait;
    private String body;
    private int commentCount;
    private String imgSmall;
    private String imgBig;
    private int type;


    public TweetBean(Long dbID, int id, String author, String pubDate, int authorid, String portrait, String body,
                     int commentCount, String imgSmall, String imgBig, int type) {
        this.dbID = dbID;
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.authorid = authorid;
        this.portrait = portrait;
        this.body = body;
        this.commentCount = commentCount;
        this.imgSmall = imgSmall;
        this.imgBig = imgBig;
        this.type = type;
    }

    public TweetBean( int id, String author, String pubDate, long pubDateLong, int authorid, String portrait, String body,
                     int commentCount, String imgSmall, String imgBig, int type) {
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.authorid = authorid;
        this.portrait = portrait;
        this.body = body;
        this.commentCount = commentCount;
        this.imgSmall = imgSmall;
        this.imgBig = imgBig;
        this.type = type;
    }

    @Generated(hash = 1304586158)
    public TweetBean(Long dbID, int id, String author, String pubDate, long pubDateLong, int authorid, String portrait, String body,
            int commentCount, String imgSmall, String imgBig, int type) {
        this.dbID = dbID;
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.authorid = authorid;
        this.portrait = portrait;
        this.body = body;
        this.commentCount = commentCount;
        this.imgSmall = imgSmall;
        this.imgBig = imgBig;
        this.type = type;
    }

    @Generated(hash = 407799815)
    public TweetBean() {
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

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getImgSmall() {
        return this.imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getImgBig() {
        return this.imgBig;
    }

    public void setImgBig(String imgBig) {
        this.imgBig = imgBig;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(pubDate);
        dest.writeLong(pubDateLong);
        dest.writeInt(authorid);
        dest.writeString(portrait);
        dest.writeString(body);
        dest.writeInt(commentCount);
        dest.writeString(imgSmall);
        dest.writeString(imgBig);

    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public TweetBean(Parcel parcel) {
        this.id = parcel.readInt();
        this.author = parcel.readString();
        this.pubDate = parcel.readString();
        this.pubDateLong = parcel.readLong();
        this.authorid = parcel.readInt();
        this.portrait = parcel.readString();
        this.body = parcel.readString();
        this.commentCount = parcel.readInt();
        this.imgSmall = parcel.readString();
        this.imgBig = parcel.readString();

    }

    public static final Creator<TweetBean> CREATOR = new Creator<TweetBean>() {
        @Override
        public TweetBean createFromParcel(Parcel parcel) {
            return new TweetBean(parcel);
        }

        @Override
        public TweetBean[] newArray(int i) {
            return new TweetBean[i];
        }
    };
}
