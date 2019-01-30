package com.example.android.newsapp2;

import android.util.Log;

public class News {

    private String newsTitle;
    private String newsPubDate;
    private String newsImageURL;
    private String newsDesc;
    private String newsLink;

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsLink() {

        return newsLink;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsPubDate() {
        return newsPubDate;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setNewsPubDate(String newsPubDate) {
        this.newsPubDate = newsPubDate;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc.split("<")[0];

    }
}
