package com.example.astronaut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class result extends AppCompatActivity {

    //"game over" sound
    MediaPlayer sound_game_over;
    LottieAnimationView lottieAnimationViewUfo;

    MediaPlayer winner_sound;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        sound_game_over = MediaPlayer.create(getApplicationContext(), R.raw.game_over_sound);
        sound_game_over.start();

        lottieAnimationViewUfo=findViewById(R.id.lottieUfo);

        lottieAnimationViewUfo.animate().translationY(0);

        TextView scoreLabel=(TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLabel=(TextView)findViewById(R.id.highScoreLabel);

        int score=getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score+"");

        SharedPreferences settings=getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);


        int highScore=settings.getInt("HIGH_SCORE",0);
        int topScore1=settings.getInt("HIGH_SCORE1",0);
        int topScore2=settings.getInt("HIGH_SCORE2",0);
        int topScore3=settings.getInt("HIGH_SCORE3",0);




        if(score>highScore){
            highScoreLabel.setText(getString(R.string.high_score)+score);
            winner_sound = MediaPlayer.create(getApplicationContext(), R.raw.cheer1);
            winner_sound.start();

            //save score
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }else{
            //highScoreLabel.setText("High Score : "+highScore);
            highScoreLabel.setText(getString(R.string.high_score)+highScore);

        }

        if(score>topScore3){
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE3",score);
            editor.commit();
        }
        if(score>topScore2){
            int temp=topScore2;
            topScore2=score;
            topScore3=temp;
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE3",topScore3);
            editor.putInt("HIGH_SCORE2",topScore2);
            editor.commit();
        }
        if(score>topScore1){
            int temp=topScore1;
            topScore1=score;
            topScore2=temp;
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE2",topScore2);
            editor.putInt("HIGH_SCORE1",topScore1);

            editor.commit();
        }

    }


    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(), start.class));
    }




    @Override
    protected void onPause() {
        super.onPause();
        sound_game_over.stop();
        sound_game_over.release();
    }
    @Override
    protected void onResume() {
        super.onResume();

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