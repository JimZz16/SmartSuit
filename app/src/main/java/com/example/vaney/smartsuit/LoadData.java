package com.example.vaney.smartsuit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class LoadData extends AppCompatActivity {
    private int num = 0;
    String timestamp, link = null;
    String file_name1 = "file1";

    private EditText editText;
    private TextView TextField, textView;
    private CheckBox box;
    private ConstraintLayout layoutIn, layoutDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        TextField = (TextView) findViewById(R.id.TextField);
        TextField.setMovementMethod(new ScrollingMovementMethod());
        box = (CheckBox) findViewById(R.id.deleteorload);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView3);

        layoutIn = (ConstraintLayout) findViewById(R.id.layoutInternal);
        layoutDe = (ConstraintLayout) findViewById(R.id.layoutDelete);

        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (box.isChecked()) {
                    layoutDe.setVisibility(layoutDe.VISIBLE);
                    layoutIn.setVisibility(layoutIn.INVISIBLE);
                } else {
                    layoutDe.setVisibility(layoutDe.INVISIBLE);
                    layoutIn.setVisibility(layoutIn.VISIBLE);
                }
            }
        });

        textView.setTextIsSelectable(true);
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(file_name1);
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

    public void MainMenu(View view){
        Intent intent = new Intent(LoadData.this, MainActivity.class);
        startActivity(intent);
    }

    public void send(View view){
        new SendRequest().execute();
        num = 1;

    }

    public void delete(View view){
        new SendRequest().execute();
        num = 2;
        editText.setText("");
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                if(num == 1){
                    link = "http://11502348.pxl-ea-ict.be/DataSmartSuit/GetFiles.php";
                }else if(num == 2){
                    link = "http://11502348.pxl-ea-ict.be/DataSmartSuit/DeleteFile.php";
                }

                URL url = new URL(link);

                JSONObject postDataParams = new JSONObject();

                timestamp = editText.getText().toString();

                //postDataParams.put("var", message.toString());
                if(num == 2){
                    postDataParams.put("var", timestamp);
                }

                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                //After uploading make String empty
                //messageSensors = "";

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            /*Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();*/
            textView.setText(result);
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}
