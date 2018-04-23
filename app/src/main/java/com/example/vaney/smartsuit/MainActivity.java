package com.example.vaney.smartsuit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public void calibrate(View view){
        Intent intent = new Intent(MainActivity.this, CalibrateStep1.class);
        startActivity(intent);
    }

    public void start(View view){
        Intent intent = new Intent(MainActivity.this, RealTimeAnimation.class);
        startActivity(intent);
    }

    public void UserManual(View view){
        Intent intent = new Intent(MainActivity.this, UserManual.class);
        startActivity(intent);
    }

    public void exit(View view){
        finish();
        moveTaskToBack(true);
    }
}
