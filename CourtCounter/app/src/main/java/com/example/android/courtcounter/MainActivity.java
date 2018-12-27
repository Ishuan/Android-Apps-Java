package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA(0);
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(scoreTeamA));
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
    * Update the score when +3 Points button was clicked - Team A
    * */
    public void threePointerTeamA(View view){
        scoreTeamA += 3;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Update the score when +2 Points button was clicked - Team A
     * */
    public void twoPointerTeamA(View view){
        scoreTeamA += 2;
        displayForTeamA(scoreTeamA);

    }

    /**
     * Update the score when +3 Points button was clicked - Team A
     * */
    public void freeShotTeamA(View view){
        scoreTeamA++;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Update the score when +3 Points button was clicked - Team B
     * */
    public void threePointerTeamB(View view){
        scoreTeamB += 3;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Update the score when +2 Points button was clicked - Team B
     * */
    public void twoPointerTeamB(View view){
        scoreTeamB += 2;
        displayForTeamB(scoreTeamB);

    }

    /**
     * Update the score when +1 Points button was clicked - Team B
     * */
    public void freeShotTeamB(View view){
        scoreTeamB++;
        displayForTeamB(scoreTeamB);
    }


    /**
     * This method is to reset a score
    * */
    public void resetScore(View view){
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }
}
