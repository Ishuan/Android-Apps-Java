package com.example.android.newsapp;

public class News {

    private String newsTitle;
    private String newsPubDate;
    private String newsImageURL;
    private String newsDesc;

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
        this.newsDesc = newsDesc;
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
}
