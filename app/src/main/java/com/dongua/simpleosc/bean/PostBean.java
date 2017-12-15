
package com.dongua.simpleosc.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
@Entity
public class PostBean implements INewsBean {

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

    @Override
    public String getPubDate() {
        return pubDate;
    }


    @Override
    public long getPubDateLong() {
        return this.pubDateLong;
    }


}
