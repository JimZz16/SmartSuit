package com.example.vaney.smartsuit;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CalibrateStep2 extends AppCompatActivity {

    TextView timer;
    private Handler mHandler = new Handler();

    String TimeLeftText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate_step2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public void InfoStep2(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CalibrateStep2.this);
        View mView = getLayoutInflater().inflate(R.layout.infostep2, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void StartStep2(View view){
        //CountdownTimer
        timer = (TextView) findViewById(R.id.timer);
        timer.setText(TimeLeftText);

        new CountDownTimer(7000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

                timer.setText(""+String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) + " sec.");
            }

            public void onFinish() {
                timer.setText("00 sec.");
            }
        }.start();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CalibrateStep2.this, MainActivity.class);
                startActivity(intent);
            }
        }, 7000);
    }
}
