package com.dongua.simpleosc.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class News {

    @Id(autoincrement = true)
    private Long id;
    private String title;
    private int newsId;
    private String author;
    private int authorId;
    private String type;
    private String pubDate;
    private int commentCount;
    private int object;


    public News(String title, int newsId, String author, int authorid, String type, String pubDate, int commentCount, int object) {
        this.title = title;
        this.newsId = newsId;
        this.author = author;
        this.authorId = authorid;
        this.type = type;
        this.pubDate = pubDate;
        this.commentCount = commentCount;
        this.object = object;
    }

    @Generated(hash = 304419995)
    public News(Long id, String title, int newsId, String author, int authorId, String type, String pubDate, int commentCount,
            int object) {
        this.id = id;
        this.title = title;
        this.newsId = newsId;
        this.author = author;
        this.authorId = authorId;
        this.type = type;
        this.pubDate = pubDate;
        this.commentCount = commentCount;
        this.object = object;
    }

    @Generated(hash = 1579685679)
    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
