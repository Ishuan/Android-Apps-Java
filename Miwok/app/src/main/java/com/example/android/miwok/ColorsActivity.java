package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> colorList = new ArrayList<>();

        colorList.add(new Word("red", "weṭeṭṭi", R.drawable.color_red));
        colorList.add(new Word("green", "chokokki", R.drawable.color_green));
        colorList.add(new Word("brown", "ṭakaakki", R.drawable.color_brown));
        colorList.add(new Word("gray", "ṭopoppi", R.drawable.color_gray));
        colorList.add(new Word("black", "kululli", R.drawable.color_black));
        colorList.add(new Word("white", "kelelli", R.drawable.color_white));
        colorList.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow));
        colorList.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, colorList, R.color.category_colors);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}
