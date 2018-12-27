package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> phraseList = new ArrayList<>();

        phraseList.add(new Word("Where are you going?", "minto wuksus"));
        phraseList.add(new Word("What is your name?", "tinnә oyaase'nә"));
        phraseList.add(new Word("My name is...", "oyaaset..."));
        phraseList.add(new Word("How are you feeling?", "michәksәs?"));
        phraseList.add(new Word("I’m feeling good.", "kuchi achit"));
        phraseList.add(new Word("Are you coming?", "әәnәs'aa?"));
        phraseList.add(new Word("Yes, I’m coming.", "hәә’ әәnәm"));
        phraseList.add(new Word("I’m coming.", "әәnәm"));
        phraseList.add(new Word("Let’s go.", "yoowutis"));
        phraseList.add(new Word("Come here.", "әnni'nem"));

        WordAdapter adapter = new WordAdapter(this, phraseList, R.color.category_phrases);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}
