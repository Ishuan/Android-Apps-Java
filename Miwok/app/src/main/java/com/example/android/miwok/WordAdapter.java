package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mCatColor;

    public WordAdapter(Activity context, ArrayList<Word> words, int catColor) {
        super(context, 0, words);
        mCatColor = catColor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView engTextView = listItemView.findViewById(R.id.default_text_view);
        engTextView.setText(currentWord.getDefaultTranslation());

        ImageView imageView = listItemView.findViewById(R.id.image);

        if (currentWord.hasImage())
            imageView.setImageResource(currentWord.getImageID());
        else
            imageView.setVisibility(View.GONE);

        View textContainerView = listItemView.findViewById(R.id.text_view_container);
        int color = ContextCompat.getColor(getContext(),mCatColor);
        textContainerView.setBackgroundColor(color);

        return listItemView;
    }
}
