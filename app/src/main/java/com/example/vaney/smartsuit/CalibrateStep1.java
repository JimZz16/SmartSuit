package com.example.vaney.smartsuit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CalibrateStep1 extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    private TextView timer, countdown;
    private Button start;

    String TimeLeftText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate_step1);

        timer = (TextView) findViewById(R.id.timer);
        countdown = (TextView) findViewById(R.id.countdown);
        start = (Button) findViewById(R.id.StartStep1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public void InfoStep1(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CalibrateStep1.this);
        View mView = getLayoutInflater().inflate(R.layout.infostep1, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void StartStep1(View view){
        //CountdownTimer
        countdown.setText("Countdown:");
        timer.setText("10 sec.");
        start.setText("In Progress");

        new CountDownTimer(10000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

                timer.setText(""+String.format("%02d",TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) + " sec.");
            }

            public void onFinish() {

                timer.setText("00 sec.");
            }
        }.start();


        //10 seconds delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mediaPlayer.stop();
                Intent intent = new Intent(CalibrateStep1.this, CalibrateStep2.class);
                startActivity(intent);
            }
        }, 10000);
    }
}
