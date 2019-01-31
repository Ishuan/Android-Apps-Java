package com.example.android.newsorgapp;

import android.os.Parcelable;

import java.io.Serializable;

public class News implements Serializable {

    private String newsTitle;
    private String newsPubDate;
    private String newsImageURL;
    private String newsDesc;
    private String newsLink;

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsPubDate() {
        return newsPubDate;
    }

    public void setNewsPubDate(String newsPubDate) {
        this.newsPubDate = newsPubDate;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }
}
