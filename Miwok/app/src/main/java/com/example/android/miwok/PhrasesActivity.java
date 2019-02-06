package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> phraseList = new ArrayList<>();

        phraseList.add(new Word("Where are you going?", "minto wuksus",
                R.raw.phrase_where_are_you_going));
        phraseList.add(new Word("What is your name?", "tinnә oyaase'nә",
                R.raw.phrase_what_is_your_name));
        phraseList.add(new Word("My name is...", "oyaaset...",
                R.raw.phrase_my_name_is));
        phraseList.add(new Word("How are you feeling?", "michәksәs?",
                R.raw.phrase_how_are_you_feeling));
        phraseList.add(new Word("I’m feeling good.", "kuchi achit",
                R.raw.phrase_im_feeling_good));
        phraseList.add(new Word("Are you coming?", "әәnәs'aa?",
                R.raw.phrase_are_you_coming));
        phraseList.add(new Word("Yes, I’m coming.", "hәә’ әәnәm",
                R.raw.phrase_yes_im_coming));
        phraseList.add(new Word("I’m coming.", "әәnәm",
                R.raw.phrase_im_coming));
        phraseList.add(new Word("Let’s go.", "yoowutis",
                R.raw.phrase_lets_go));
        phraseList.add(new Word("Come here.", "әnni'nem",
                R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, phraseList, R.color.category_phrases);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(getApplicationContext(),phraseList.get(position).getmAudioID());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
