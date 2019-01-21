package com.example.android.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLAsync extends AsyncTask<String, Void, String> {

    private String[] newURL;
    private String keyword;
    Bitmap bitmap = null;

    public URLAsync(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        StringBuilder builder = new StringBuilder();
        HttpURLConnection httpURLConnection;
        BufferedReader reader;
        try {
            url = new URL(strings[0] + "?keyword=" + keyword);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null)
                    builder.append(line);
                newURL = builder.toString().split(".jpg");
                for (String imageURL : newURL) {
                    System.out.println(imageURL);
                   /* getImage(imageURL);*/
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

 /*   private void getImage(String stringURL) {
        HttpURLConnection connection = null;
        try {
            URL imageLink = new URL(stringURL + ".jpg");
            connection = (HttpURLConnection) imageLink.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }*/
}