
package com.dongua.simpleosc.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

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
