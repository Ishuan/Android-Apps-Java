package com.example.android.newsapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {

    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection = null;
    private INewsParse iNewsParse;
    private Context context;

     NewsAsyncTask(INewsParse iNewsParse, Context context) {
        this.iNewsParse = iNewsParse;
        this.context = context;
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {

        ArrayList<News> parsedNews = null;

        try {
            URL url = new URL(strings[0]);
            Log.d("Demo","URL: "+url );
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               parsedNews =  XMLParser.parseNews(httpURLConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return parsedNews;
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
        iNewsParse.sendParseNews(news);

    }

    public interface  INewsParse{
        public void sendParseNews(ArrayList<News> news);
    }
}
