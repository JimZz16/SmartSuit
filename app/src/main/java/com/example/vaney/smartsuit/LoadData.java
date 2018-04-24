package com.example.vaney.smartsuit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadData extends AppCompatActivity {

    String file_name1 = "file1";
    String file_name2 = "file2";
    String file_name3 = "file3";
    String file_name = file_name1;

    CheckBox file1, file2, file3;
    TextView TextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        file1 = (CheckBox) findViewById(R.id.check1);
        file2 = (CheckBox) findViewById(R.id.check2);
        file3 = (CheckBox) findViewById(R.id.check3);

        TextField = (TextView) findViewById(R.id.TextField);
        TextField.setMovementMethod(new ScrollingMovementMethod());

        filesCheckbox();
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            TextField.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void filesCheckbox(){
        file1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file1.isChecked()) {
                    if(file2.isChecked()){
                        file2.toggle();
                    }
                    if(file3.isChecked()){
                        file3.toggle();
                    }
                }

                file_name = file_name1;
            }
        });

        file2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file2.isChecked()) {
                    if(file3.isChecked()){
                        file3.toggle();
                    }
                    if(file1.isChecked()){
                        file1.toggle();
                    }
                }
                file_name = file_name2;
            }
        });

        file3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file3.isChecked()) {
                    if(file2.isChecked()){
                        file2.toggle();
                    }
                    if(file1.isChecked()){
                        file1.toggle();
                    }
                }
                file_name = file_name3;
            }
        });
    }

    public void MainMenu(View view){
        Intent intent = new Intent(LoadData.this, MainActivity.class);
        startActivity(intent);
    }
}
