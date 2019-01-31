package com.example.android.newsorgapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements NewsAsyncTask.IParseNews {

    private final String API_URL =
            "https://newsapi.org/v2/top-headlines?country=us&apiKey=d5da01439a5d42b0b1e1cf07533d342f";
    private ListView categoryListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String recvdCat = getIntent().getExtras().getString(MainActivity.CAT_NAME);
        categoryListView = findViewById(R.id.categoryListView);

        if(getIntent().getExtras() != null && recvdCat.length()!= 0){
            setTitle(recvdCat);
            new NewsAsyncTask(CategoryActivity.this,CategoryActivity.this).execute(API_URL+"&category="+recvdCat);
        }
    }

    @Override
    public void parseNews(final ArrayList<News> parsedNews) {

        if(parsedNews.size()==0)
            Toast.makeText(getApplicationContext(),"No News for this category",Toast.LENGTH_SHORT).show();

        NewsAdapter adapter = new NewsAdapter(getApplicationContext(),R.layout.list_view,parsedNews);
        categoryListView.setAdapter(adapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CategoryActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("News",parsedNews);
                bundle.putInt("newsIndex",i);
                intent.putExtra("newsBundle",bundle);
                startActivity(intent);
            }
        });
    }
}
