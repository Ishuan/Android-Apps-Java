package com.example.android.newsapp2;

import android.util.Log;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XMLParser {


    public static ArrayList<News> parseNews(InputStream inputStream) throws XmlPullParserException, IOException {

        ArrayList<News> newsList = new ArrayList<>();
        News news = null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser pullParser = factory.newPullParser();
        pullParser.setInput(inputStream, "UTF-8");


        int event = pullParser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT) {

            switch (event) {

                case XmlPullParser.START_TAG:
                    if (pullParser.getName().equals("title")) {
                        news = new News();
                        news.setNewsTitle(pullParser.nextText().trim());
                        Log.d("Demo","Obeject: "+news);
                    } if (pullParser.getName().equals("description"))
                        news.setNewsDesc(pullParser.nextText().trim());
                     if (pullParser.getName().equals("pubDate"))
                        news.setNewsPubDate(pullParser.nextText().trim());
                     if(pullParser.getName().equals("link"))
                        news.setNewsLink(pullParser.nextText().trim());
                     if (pullParser.getName().equals("group")) {
                        int event2 = pullParser.next();
                        switch (event2) {
                            case XmlPullParser.START_TAG:
                                if (pullParser.getName().equalsIgnoreCase("content")) {
                                    news.setNewsImageURL(pullParser.getAttributeValue(null, "url"));
                                    break;
                                }
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (pullParser.getName().equals("item"))
                        newsList.add(news);
                    break;

                default:
                    break;
            }
            event = pullParser.next();
        }
        return newsList;
    }
}

