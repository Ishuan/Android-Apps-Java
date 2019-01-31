package com.example.android.newsorgapp;

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
    private Context context;
    private IParseNews iParseNews;

    NewsAsyncTask(Context context, IParseNews iParseNews) {
        this.context = context;
        this.iParseNews = iParseNews;
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {

        ArrayList<News> parsedNewsList = new ArrayList<>();

        HttpsURLConnection httpsURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String token = "";
                if ((token = bufferedReader.readLine()) != null)
                    builder.append(token);

                JSONObject jsonRootObject = new JSONObject(builder.toString());
                JSONArray jsonNewsArray = jsonRootObject.getJSONArray("articles");
                for (int i = 0; i < jsonNewsArray.length(); i++) {
                    JSONObject jsonNewsObj = jsonNewsArray.getJSONObject(i);
                    News newsObj = new News();
                    newsObj.setNewsTitle(jsonNewsObj.getString("title"));
                    newsObj.setNewsPubDate(jsonNewsObj.getString("publishedAt"));
                    newsObj.setNewsImageURL(jsonNewsObj.getString("urlToImage"));
                    newsObj.setNewsDesc(jsonNewsObj.getString("description"));
                    newsObj.setNewsLink(jsonNewsObj.getString("url"));

                    parsedNewsList.add(newsObj);
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
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpsURLConnection.disconnect();
        }

        return parsedNewsList;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading News...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        progressDialog.dismiss();
        iParseNews.parseNews(news);
    }

    public interface IParseNews {
        void parseNews(ArrayList<News> parsedNews);
    }
}
