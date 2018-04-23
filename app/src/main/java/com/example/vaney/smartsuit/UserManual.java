package com.example.vaney.smartsuit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserManual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manual);
    }

    public void BackToMainMenu(View view){
        Intent intent = new Intent(UserManual.this, MainActivity.class);
        startActivity(intent);
    }
}
