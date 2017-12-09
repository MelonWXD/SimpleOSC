package com.dongua.simpleosc.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class News {

    @Id(autoincrement = true)
    private Long dbID;
    private int id;
    private String author;
    private String pubDate;
    private String title;
    private int authorid;
    private int commentCount;
    private String type;


    public News(int id, String author, String pubDate, String title,
                int authorid, int commentCount, String type) {
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.title = title;
        this.authorid = authorid;
        this.commentCount = commentCount;
        this.type = type;
    }

    @Generated(hash = 971210210)
    public News(Long dbID, int id, String author, String pubDate, String title,
                int authorid, int commentCount, String type) {
        this.dbID = dbID;
        this.id = id;
        this.author = author;
        this.pubDate = pubDate;
        this.title = title;
        this.authorid = authorid;
        this.commentCount = commentCount;
        this.type = type;
    }

    @Generated(hash = 1579685679)
    public News() {
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
