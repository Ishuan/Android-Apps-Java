package com.example.android.photogallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements URLAsync.SendImage {

    TextView searchWord;
    ImageView keyWordImage;
    ImageButton prevImage;
    ImageButton nextImage;
    String[] keywordURLList;
    int numberOfImages = 0;
    int currentImage = 0;
    Bitmap bitmap;
    public  static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Button goBtn = findViewById(R.id.goBtn);
        searchWord = findViewById(R.id.searchBar);
        keyWordImage = findViewById(R.id.imageView);
        prevImage = findViewById(R.id.prevBtn);
        nextImage = findViewById(R.id.nextBtn);

        prevImage.setEnabled(false);
        nextImage.setEnabled(false);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    currentImage = 0;
                    new GetKeywords().execute("http://dev.theappsdr.com/apis/photos/keywords.php");
                } else
                    Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_SHORT).show();
            }
        });

        nextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentImage==numberOfImages-1)
                    currentImage = 0;
                else
                    currentImage++;
                new URLAsync(MainActivity.this,MainActivity.this)
                        .execute("http://dev.theappsdr.com/apis/photos/index.php",
                        searchWord.getText().toString(),String.valueOf(currentImage));
            }
        });

        prevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentImage==0)
                    currentImage = numberOfImages-1;
                else
                    currentImage--;
                new URLAsync(MainActivity.this,MainActivity.this)
                        .execute("http://dev.theappsdr.com/apis/photos/index.php",
                                searchWord.getText().toString(),String.valueOf(currentImage));
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    @Override
    public void setImage(Bitmap b) {
        this.bitmap = b;
        if (bitmap != null)
            keyWordImage.setImageBitmap(bitmap);
    }

    private class GetKeywords extends AsyncTask<String, Void, Void> {

        CharSequence[] keywordArray = null;
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        protected void onPostExecute(Void aVoid) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Choose a Keyword")
                    .setItems(keywordArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            searchWord.setText(keywordArray[i]);
                            new GETKeyWordURL(searchWord.getText().toString()).execute("http://dev.theappsdr.com/apis/photos/index.php");
                        }
                    });
            builder.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        stringBuilder.append(line);
                    keywordArray = stringBuilder.toString().split(";");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

    private class GETKeyWordURL extends AsyncTask<String, Void, Void> {

        String keyword;
        Bitmap bitmap1;
        ProgressDialog progressDialog = null;

        GETKeyWordURL(String keyword) {
            this.keyword = keyword;
        }

        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            StringBuilder builder = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            keywordURLList = null;
            try {
                url = new URL(strings[0] + "?keyword=" + keyword);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null)
                        builder.append(line);
                    keywordURLList = builder.toString().split(".jpg");
                    if (keywordURLList.length == 0)
                        bitmap1 = null;

                    else
                        numberOfImages = keywordURLList.length;


                    url = new URL(keywordURLList[0] + ".jpg");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    bitmap1 = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading Dictionary...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            if (bitmap1 != null) {
                keyWordImage.setImageBitmap(bitmap1);
                prevImage.setEnabled(true);
                nextImage.setEnabled(true);
            }
            else {
                Toast.makeText(MainActivity.this, "No image for this keyword", Toast.LENGTH_SHORT).show();
                keyWordImage.setImageBitmap(bitmap1);
                prevImage.setEnabled(false);
                nextImage.setEnabled(false);
            }
        }
    }
}