package com.example.astronaut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScores extends AppCompatActivity  {

    MediaPlayer sound_High_scores;

    LottieAnimationView lottieAnimationViewWinners;

    TextView tv_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        


        lottieAnimationViewWinners=findViewById(R.id.lottieWinners);

        lottieAnimationViewWinners.animate().translationY(0);


        sound_High_scores = MediaPlayer.create(getApplicationContext(), R.raw.highscores);
        sound_High_scores.start();


        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        TextView highScoreLabel1=(TextView)findViewById(R.id.highScoreLabel1);
        TextView highScoreLabel2=(TextView)findViewById(R.id.highScoreLabel2);
        TextView highScoreLabel3=(TextView)findViewById(R.id.highScoreLabel3);

        //highScoreLabel1.setText("First place : "+settings.getInt("HIGH_SCORE1",0));
        highScoreLabel1.setText(getString(R.string.first_place)+settings.getInt("HIGH_SCORE1",0));
        highScoreLabel2.setText(getString(R.string.second_place)+settings.getInt("HIGH_SCORE2",0));
        highScoreLabel3.setText(getString(R.string.third_place)+settings.getInt("HIGH_SCORE3",0));

    }

    //back to start menu from HighScores menu
    public void backToStart(View view){
        startActivity(new Intent(getApplicationContext(), start.class));
    }
    //CANCEL RETURN BUTTON ON DEVICE
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            switch(event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sound_High_scores.stop();
        sound_High_scores.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}