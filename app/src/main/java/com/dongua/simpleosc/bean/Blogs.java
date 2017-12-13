package com.dongua.simpleosc.bean;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by duoyi on 17-12-13.
 */

public class Blogs {

    private Long dbID;
    private int id;
    private String author;
    private String pubDate;
    private long pubDateLong;
    private String title;
    private int authorid;
    private int commentCount;
    private String type;

    public Blogs(int id, String author, String pubDate, long pubDateLong, String title, int authorid, int commentCount, String type) {
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.pubDateLong = pubDateLong;
        this.title = title;
        this.authorid = authorid;
        this.commentCount = commentCount;
        this.type = type;
    }

    public Long getDbID() {
        return dbID;
    }

    public void setDbID(Long dbID) {
        this.dbID = dbID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getPubDateLong() {
        return pubDateLong;
    }

    public void setPubDateLong(long pubDateLong) {
        this.pubDateLong = pubDateLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
