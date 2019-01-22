package com.example.android.photogallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLAsync extends AsyncTask<String, Void, Bitmap> {

    private String[] newURL;
    private SendImage setBitmap;
    private ProgressDialog progressDialog;
    private WeakReference<Context> context;

    public URLAsync(SendImage setBitmap, Context context) {
        this.setBitmap = setBitmap;
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context.get());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Photo...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        progressDialog.dismiss();
        setBitmap.setImage(s);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        URL url;
        StringBuilder builder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        try {
            url = new URL(strings[0] + "?keyword=" + strings[1]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null)
                    builder.append(line);
                newURL = builder.toString().split(".jpg");
                url = new URL(newURL[Integer.parseInt(strings[2])] + ".jpg");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpURLConnection.disconnect();
        }
        return bitmap;
    }

    public interface SendImage {
        void setImage(Bitmap bitmap);
    }
}