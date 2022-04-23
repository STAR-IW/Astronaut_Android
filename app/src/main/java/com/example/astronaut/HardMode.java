package com.example.astronaut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class HardMode extends AppCompatActivity {


    MediaPlayer mediaPlayer_in_game;

    private Button pauseBtn;

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView astronaut;
    private ImageView star;
    private ImageView earth;
    private ImageView alien;

    //Size
    private int frameHeight;
    private int astronautSize;
    private int screenWidth;
    private int screenHeight;

    //POSITION ON SCREEN
    private int astronautY;
    private int starX;
    private int starY;
    private int earthX;
    private int earthY;
    private int alienX;
    private int alienY;

    //SPEED OF ELEMENTS
    private int astronautSpeed;
    private int starSpeed;
    private int earthSpeed;
    private int alienSpeed;

    private int score=0;


    private Handler handler=new Handler();
    private Timer timer=new Timer();
    private SoundPlayer sound;
    LottieAnimationView lottieAnimationViewFinger;



    private boolean pause_flg=false;
    private boolean action_flg=false;
    private boolean start_flg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_mode);
        lottieAnimationViewFinger=findViewById(R.id.lottiefinger2);


        mediaPlayer_in_game = MediaPlayer.create(getApplicationContext(), R.raw.in_game_music);
        mediaPlayer_in_game.start();
        mediaPlayer_in_game.setLooping(true);


        sound=new SoundPlayer(this);

        scoreLabel=(TextView) findViewById(R.id.scoreLabel);
        startLabel=(TextView)findViewById(R.id.startLabel);
        astronaut =(ImageView)findViewById(R.id.astronaut);
        star =(ImageView)findViewById(R.id.star);
        earth =(ImageView)findViewById(R.id.earth);
        alien =(ImageView)findViewById(R.id.alien);

        pauseBtn=(Button)findViewById(R.id.pauseBtn);

        //GET SCREEN SIZE
        WindowManager wm=getWindowManager();
        Display disp=wm.getDefaultDisplay();
        Point size=new Point();
        disp.getSize(size);

        screenWidth=size.x;
        screenHeight=size.y;

        // COMPATIBLE TO MULTI SCREEN SIZES
        astronautSpeed =Math.round(screenHeight/60F);
        starSpeed =Math.round(screenWidth/40F);
        earthSpeed =Math.round(screenWidth/15F);
        alienSpeed =Math.round(screenWidth/25F);


        //STARTING POSITION OF ELEMENTS
        star.setX(-150);
        star.setY(-150);
        earth.setX(-150);
        earth.setY(-150);
        alien.setX(-150);
        alien.setY(-150);

        //STARTING SCORE
        scoreLabel.setText(getString(R.string.score0));

    }
    //Stop theme song when starting the game
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer_in_game.stop();
        mediaPlayer_in_game.release();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    public void pausePushed(View view){

        if(pause_flg==false){

            pause_flg=true;

            //stop timer
            timer.cancel();
            timer=null;

            //pauseBtn.setText("START");
        }else{
            pause_flg=false;
            //pauseBtn.setText("PAUSE");
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }

    }
    public void ExitToMenu(View view){
        timer.cancel();
        timer=null;
        //sound.playOverSound();

        Intent intent=new Intent(getApplicationContext(), start.class);
        intent.putExtra("SCORE",score);
        startActivity(intent);

    }

    public void changePos(){

        hitCheck();

        //star
        starX -= starSpeed;
        if(starX <0){
            starX =screenWidth+20;
            starY =(int)Math.floor(Math.random()*(frameHeight- star.getHeight()));
        }
        star.setX(starX);
        star.setY(starY);

        //alien
        alienX -= alienSpeed;
        if(alienX <0){
            alienX =screenWidth+10;
            alienY =(int)Math.floor(Math.random()*(frameHeight- alien.getHeight()));
        }
        alien.setX(alienX);
        alien.setY(alienY);

        //earth
        earthX -= earthSpeed;
        if (earthX <0){
            earthX =screenWidth+5000;
            earthY =(int)Math.floor(Math.random()*(frameHeight- earth.getHeight()));
        }
        earth.setX(earthX);
        earth.setY(earthY);


        //Move the astronaut
        if (action_flg==true){
            //On screen touch
            astronautY -= astronautSpeed;
            //On realising touch from screen
        }else {
            astronautY += astronautSpeed;
        }
        //check astronaut position
        if(astronautY <0) astronautY =0;

        if(astronautY >frameHeight- astronautSize) astronautY =frameHeight- astronautSize;

        astronaut.setY(astronautY);

        scoreLabel.setText(getString(R.string.score0)+score);

    }

    public void hitCheck(){//IF THE ASTRONAUT IS TOUCHING THE ITEMS,HIT

        //star
        int starCenterX = starX + star.getWidth()/2;
        int starCenterY = starY + star.getHeight()/2;


        if(0<= starCenterX && starCenterX <= astronautSize &&
                astronautY <= starCenterY && starCenterY <= astronautY + astronautSize){

            score+=20;
            starX =-10;
            sound.playHitSound();
        }

        //earth
        int earthCenterX = earthX + earth.getWidth()/2;
        int earthCenterY = earthY + earth.getHeight()/2;

        if(0<= earthCenterX && earthCenterX <= astronautSize &&
                astronautY <= earthCenterY && earthCenterY <= astronautY + astronautSize){
            score+=60;
            earthX =-10;
            sound.playHitSound();
        }

        //alien
        int alienCenterX = alienX + alien.getWidth()/2;
        int alienCenterY = alienY + alien.getHeight()/2;

        if(0<= alienCenterX && alienCenterX <= astronautSize &&
                astronautY <= alienCenterY && alienCenterY <= astronautY + astronautSize){

            //if you touch the alien,stop the TIMER.
            timer.cancel();
            timer=null;
            sound.playOverSound();

            //Show final score.
            Intent intent=new Intent(getApplicationContext(), result.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }
    }

    public boolean onTouchEvent(MotionEvent me){

        if(start_flg==false){

            start_flg=true;


            //IM GETTING FRAME HEIGHT AND ASTRONAUT HEIGHT HERE
            //BEACUSE THE UI HAS NOT BEEN SET ON THE SCREE IN OnCreate()
            FrameLayout frame=(FrameLayout)findViewById(R.id.frame);
            frameHeight=frame.getHeight();

            astronautY =(int) astronaut.getY();

            //astronaut is the same in height and width(square)
            astronautSize = astronaut.getHeight();

            startLabel.setVisibility(View.GONE);
            lottieAnimationViewFinger.setVisibility(View.GONE);


            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
            if(me.getAction()==MotionEvent.ACTION_DOWN){
                action_flg=true;
            }else if(me.getAction()==MotionEvent.ACTION_UP){
                action_flg=false;
            }
        }
        return true;
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