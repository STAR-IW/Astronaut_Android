package com.example.astronaut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

public class start extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //ANIMATION FOR GRADIENT IN START SCREEN
        LinearLayout linearLayout= findViewById(R.id.layout_start);
        AnimationDrawable animationDrawable=(AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        //PLAY THEME SONG ON APP LAUNCH
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);


        //ASTRONAUT ANIMATION
        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(5000);
    }

    //STOP START THEME SONG WHEN STARTING THE GAME
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startGame(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void openHighScores(View view){
        startActivity(new Intent(getApplicationContext(),HighScores.class));
    }
    public void openHowToPlay(View view){
        startActivity(new Intent(getApplicationContext(),HowToPlay.class));
    }
    public void startLevel2(View view){
        startActivity(new Intent(getApplicationContext(), HardMode.class));
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

}