package com.example.android.photogallery;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goBtn = findViewById(R.id.goBtn);
        final EditText searchWord = findViewById(R.id.searchBar);
        tempList.add("android");
        tempList.add("aurora");
        tempList.add("uncc");
        tempList.add("winter");
        tempList.add("wonders");

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
                alertdialog.setTitle("Choose a Keyword")
                        .setItems(tempList.toArray(new CharSequence[tempList.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String key = tempList.get(i);
                                searchWord.setText(key);
                            }
                        });
                alertdialog.show();
            }

        });

    }
}
