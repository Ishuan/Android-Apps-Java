package com.example.android.newsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {

    private ProgressDialog progressDialog;
    private ArrayList<News> newsList;
    private NewsInterface iNews;
    private Context context;

    public NewsAsyncTask(NewsInterface iNews, Context context) {
        this.iNews = iNews;
        this.context = context;
        newsList = new ArrayList<>();
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {

        HttpsURLConnection httpsURLConnection = null;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;

        try {
            URL url = new URL(strings[0]);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String token = "";
                while ((token = reader.readLine()) != null)
                    builder.append(token);

                JSONObject rootObject = new JSONObject(builder.toString());
                JSONArray newsArticles = rootObject.getJSONArray("articles");
                for (int i = 0; i < newsArticles.length(); i++) {
                    JSONObject newsObj = newsArticles.getJSONObject(i);
                    News news = new News();
                    news.setNewsTitle(newsObj.getString("title"));
                    news.setNewsPubDate(newsObj.getString("publishedAt"));
                    news.setNewsImageURL(newsObj.getString("urlToImage"));
                    news.setNewsDesc(newsObj.getString("description"));

                    newsList.add(news);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpsURLConnection.disconnect();
        }
        return newsList;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading News ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        progressDialog.dismiss();
        iNews.sendNewsData(news);
    }

    public interface NewsInterface {
         void sendNewsData(ArrayList<News> sendNewsList);
    }
}
