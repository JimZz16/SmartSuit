package com.example.vaney.smartsuit;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RealTimeAnimation extends AppCompatActivity{

    private ImageView MaxM, MinM, MaxC, MinC, MaxH, MinH, BaseM, BaseC, BaseH, dotM, dotC, dotH, logo;
    private int aM, aC, aH, beginValueM, beginValueC, beginValueH, value = 0;
    private TextView textM, textC, textH, textMhide, textChide, textHhide, textMhideMax, textChideMax, textHhideMax, textMhideMin, textChideMin, textHhideMin;
    private EditText range;
    private CheckBox box, sound, RangeSimpleView, file1, file2, file3;
    private RelativeLayout layout;
    private Button save;
    private int getBottomM, getLeftM, getBottomC, getLeftC, getBottomH, getLeftH, PositionC7, PositionHead, pixelsSmall, pixelsSmall2;
    private final int BeginPos = -15;
    private final int BeginPosHead = -5;
    private int toggle, rangeNum, PosHead1, PosHead2, PosHead3, PosC1, PosC2, SoundNumM, SoundNumC, SoundNumH = 0;

    private String file_name1 = "file1";
    private String file_name2 = "file2";
    private String file_name3 = "file3";
    private String file_name = file_name1;
    private String message = "";

    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_animation);

        dotM = (ImageView) findViewById(R.id.Midbackdot);
        dotC = (ImageView) findViewById(R.id.C7dot);
        dotH = (ImageView) findViewById(R.id.Headdot);
        MaxM = (ImageView) findViewById(R.id.MaxMdot);
        MaxC = (ImageView) findViewById(R.id.MaxCdot);
        MaxH = (ImageView) findViewById(R.id.MaxHdot);
        MinM = (ImageView) findViewById(R.id.MinMdot);
        MinC = (ImageView) findViewById(R.id.MinCdot);
        MinH = (ImageView) findViewById(R.id.MinHdot);
        BaseM = (ImageView) findViewById(R.id.BaselineM);
        BaseC = (ImageView) findViewById(R.id.BaselineC);
        BaseH = (ImageView) findViewById(R.id.BaselineH);
        logo = (ImageView) findViewById(R.id.ShowLogo);

        textM = (TextView) findViewById(R.id.MidbackText);
        textC = (TextView) findViewById(R.id.C7Text);
        textH = (TextView) findViewById(R.id.HeadText);
        textMhide = (TextView) findViewById(R.id.MidbackShow);
        textChide = (TextView) findViewById(R.id.C7Show);
        textHhide = (TextView) findViewById(R.id.HeadShow);
        textMhideMax = (TextView) findViewById(R.id.MidbackMax);
        textChideMax = (TextView) findViewById(R.id.C7Max);
        textHhideMax = (TextView) findViewById(R.id.HeadMax);
        textMhideMin = (TextView) findViewById(R.id.MidbackMin);
        textChideMin = (TextView) findViewById(R.id.C7Min);
        textHhideMin = (TextView) findViewById(R.id.HeadMin);

        box = (CheckBox) findViewById(R.id.checkBox);
        sound = (CheckBox) findViewById(R.id.sound);
        RangeSimpleView = (CheckBox) findViewById(R.id.SimpleView);
        file1 = (CheckBox) findViewById(R.id.check1);
        file2 = (CheckBox) findViewById(R.id.check2);
        file3 = (CheckBox) findViewById(R.id.check3);

        save = (Button) findViewById(R.id.load);

        layout = (RelativeLayout) findViewById(R.id.LayoutShowHide);

        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(box.isChecked()){
                    layout.setVisibility(layout.VISIBLE);
                    logo.setVisibility(View.INVISIBLE);
                }else{
                    layout.setVisibility(layout.INVISIBLE);
                    logo.setVisibility(View.VISIBLE);
                }
            }
        });

        getBottomM =((ViewGroup.MarginLayoutParams)dotM.getLayoutParams()).bottomMargin;
        getLeftM =((ViewGroup.MarginLayoutParams)dotM.getLayoutParams()).leftMargin;
        getBottomC =((ViewGroup.MarginLayoutParams)dotC.getLayoutParams()).bottomMargin;
        getLeftC =((ViewGroup.MarginLayoutParams)dotC.getLayoutParams()).leftMargin;
        getBottomH =((ViewGroup.MarginLayoutParams)dotH.getLayoutParams()).bottomMargin;
        getLeftH =((ViewGroup.MarginLayoutParams)dotH.getLayoutParams()).leftMargin;

        setVisibilityRange();
        setRotation();
        filesCheckbox();
    }

    public void startButton(View view){
        setReset();
        setBaseline();
        setCurrentDataZero();

        range = (EditText) findViewById(R.id.Range);
        if(range.getText().toString().matches("")){
            rangeNum = 0;
        }else{
            rangeNum = Integer.parseInt(range.getText().toString());
        }

        if(rangeNum>45){
            Toast.makeText(RealTimeAnimation.this, "The maximum range is 45!", Toast.LENGTH_SHORT).show();
            rangeNum = 45;
            range.setText(Integer.toString(rangeNum));
        }else if(rangeNum >= 0 && rangeNum < 5){
            Toast.makeText(RealTimeAnimation.this, "The minimum range is 5!", Toast.LENGTH_SHORT).show();
            rangeNum = 5;
            range.setText(Integer.toString(rangeNum));
        }

        if(rangeNum >= 20){
            pixelsSmall = 1;
            pixelsSmall2 = 2;
        }else if(rangeNum < 20){
            pixelsSmall = 2;
            pixelsSmall2 = 3;
        }
        setRange(rangeNum);

        textM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingsM(rangeNum);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingsC(rangeNum);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingsH(rangeNum);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public  void meerMidback(View view){
        textM = (TextView) findViewById(R.id.MidbackText);
        aM++;
        textM.setText(Integer.toString(aM));

        message += " " + Integer.toString(aM) + " " + Integer.toString(aC) + " " + Integer.toString(aH) + " -";
    }

    public  void minderMidback(View view){
        textM = (TextView) findViewById(R.id.MidbackText);
        aM--;
        textM.setText(Integer.toString(aM));

        message += " " + Integer.toString(aM) + " " + Integer.toString(aC) + " " + Integer.toString(aH) + " -";
    }

    public  void meerC7(View view){
        textC = (TextView) findViewById(R.id.C7Text);
        aC++;
        textC.setText(Integer.toString(aC));

        message += " " + Integer.toString(aM) + " " + Integer.toString(aC) + " " + Integer.toString(aH) + " -";
    }

    public  void minderC7(View view){
        textC = (TextView) findViewById(R.id.C7Text);
        aC--;
        textC.setText(Integer.toString(aC));

        message += " " + Integer.toString(aM) + " " + Integer.toString(aC) + " " + Integer.toString(aH) + " -";
    }

    public  void meerHead(View view){
        textH = (TextView) findViewById(R.id.HeadText);
        aH++;
        textH.setText(Integer.toString(aH));

        message += " " + Integer.toString(aM) + " " + Integer.toString(aC) + " " + Integer.toString(aH) + " -";
    }

    public  void minderHead(View view){
        textH = (TextView) findViewById(R.id.HeadText);
        aH--;
        textH.setText(Integer.toString(aH));

        message += " " + Integer.toString(aM) + " " + Integer.toString(aC) + " " + Integer.toString(aH) + " -";
    }


    public void settingsM(int range){
        value = Integer.parseInt(textM.getText().toString());
        int diff = (value - beginValueM);

        if(RangeSimpleView.isChecked()){
            if (diff <= 45 && diff >= -45) {
                textMhide.setText("Current: " + Integer.toString(diff));
                dotM.setRotation(BeginPos - diff * pixelsSmall);

                ((ViewGroup.MarginLayoutParams) dotM.getLayoutParams()).bottomMargin = -((BeginPos - diff) + 15) * pixelsSmall + getBottomM;

            } else if (diff > 45) {
                dotM.setRotation(BeginPos - 45);
                textChide.setText("Current: " + Integer.toString(45));
            } else if (diff < -45) {
                dotM.setRotation(BeginPos + 45);
                textChide.setText("Current: " + Integer.toString(-45));
            }
        }else {
            if (diff <= 45 && diff >= -45) {
                textMhide.setText("Current: " + Integer.toString(diff));

                //Formule rotatie naar positief int getal.
                PosC1 = -((BeginPos - diff) + 15) * pixelsSmall2;
                PositionC7 = PosC1 + PosC2;

                PosHead1 = -((BeginPosHead - diff) + 5) * pixelsSmall2;
                PositionHead = PosHead1 + PosHead2 + PosHead3;

                dotM.setRotation(BeginPos - diff);

                //Als Midback lijn 1 graden omhoog gaat -> C7 lijn 1 bottomMargin pixel omhoog
                ((ViewGroup.MarginLayoutParams) dotM.getLayoutParams()).bottomMargin = -((BeginPos - diff) + 15) * pixelsSmall + getBottomM;
                ((ViewGroup.MarginLayoutParams) dotC.getLayoutParams()).bottomMargin = PositionC7 + getBottomC;
                ((ViewGroup.MarginLayoutParams) dotH.getLayoutParams()).bottomMargin = PositionHead + getBottomH;

            } else if (diff > 45) {
                dotM.setRotation(BeginPos - 45);
                textChide.setText("Current: " + Integer.toString(45));

                ((ViewGroup.MarginLayoutParams) dotM.getLayoutParams()).bottomMargin = (45 * pixelsSmall) + getBottomM;
            } else if (diff < -45) {
                dotM.setRotation(BeginPos + 45);
                textChide.setText("Current: " + Integer.toString(-45));
                ((ViewGroup.MarginLayoutParams) dotM.getLayoutParams()).bottomMargin = (-45 * pixelsSmall) + getBottomM;
            }
        }

        if(sound.isChecked()){
            if(diff > range){
                SoundNumM++;
                if(SoundNumM == 1){
                    tg.startTone(ToneGenerator.TONE_PROP_ACK);
                }else if(SoundNumM > 1){
                    SoundNumM = 2;
                }
            }else if(diff <= range & diff >= -range){
                SoundNumM = 0;
            }else if(diff < -range){
                SoundNumM++;
                if(SoundNumM == 1){
                    tg.startTone(ToneGenerator.TONE_PROP_ACK);
                }else if(SoundNumM > 1){
                    SoundNumM = 2;
                }
            }
        }
    }

    public void settingsC(int range){
        value = Integer.parseInt(textC.getText().toString());
        int diff = (value - beginValueC);

        if(RangeSimpleView.isChecked()) {
            if (diff <= 45 && diff >= -45) {
                dotC.setRotation(BeginPos - diff * pixelsSmall);//1 omhoog als dotM 1 graden omhoog gaat!
                textChide.setText("Current: " + Integer.toString(diff));

                ((ViewGroup.MarginLayoutParams) dotC.getLayoutParams()).bottomMargin = -((BeginPos - diff) + 15) * pixelsSmall + getBottomC;
            } else if (diff > 45) {
                dotC.setRotation(BeginPos - 45);
                textChide.setText("Current: " + Integer.toString(45));
            } else if (diff < -45) {
                dotC.setRotation(BeginPos + 45);
                textChide.setText("Current: " + Integer.toString(-45));
            }
        }else{
            if (diff <= 45 && diff >= -45) {
                PosC2 = -((BeginPos - diff) + 15) * 1;
                PositionC7 = PosC1 + PosC2;

                //PositionHead = PositionHead + (diff * 2);
                PosHead2 = -((BeginPosHead - diff) + 5) * 2;
                PositionHead = PosHead1 + PosHead2 + PosHead3;

                dotC.requestLayout();
                ((ViewGroup.MarginLayoutParams) dotC.getLayoutParams()).bottomMargin = PositionC7 + getBottomC;
                ((ViewGroup.MarginLayoutParams) dotH.getLayoutParams()).bottomMargin = PositionHead + getBottomH;
                dotC.requestLayout();

                dotC.setRotation(BeginPos - diff);//1 omhoog als dotM 1 graden omhoog gaat!

                textChide.setText("Current: " + Integer.toString(diff));
            } else if (diff > 45) {
                dotC.setRotation(BeginPos - 45);

                textChide.setText("Current: " + Integer.toString(45));
            } else if (diff < -45) {
                dotC.setRotation(BeginPos + 45);

                textChide.setText("Current: " + Integer.toString(-45));
            }
        }


        if(sound.isChecked()){
            if(diff > range){
                SoundNumC++;
                if(SoundNumC == 1){
                    tg.startTone(ToneGenerator.TONE_PROP_ACK);
                }else if(SoundNumC > 1){
                    SoundNumC = 2;
                }
            }else if(diff <= range & diff >= -range){
                SoundNumC = 0;
            }else if(diff < -range){
                SoundNumC++;
                if(SoundNumC == 1){
                    tg.startTone(ToneGenerator.TONE_PROP_ACK);
                }else if(SoundNumC > 1){
                    SoundNumC = 2;
                }
            }
        }
    }

    public void settingsH(int range) {
        value = Integer.parseInt(textH.getText().toString());
        int diff = (value - beginValueH);

        if(RangeSimpleView.isChecked()){
            if(diff <= 45 && diff >= -45) {
                dotH.setRotation(BeginPosHead - diff * pixelsSmall);
                textHhide.setText("Current: "+Integer.toString(diff));

                ((ViewGroup.MarginLayoutParams) dotH.getLayoutParams()).bottomMargin = -((BeginPos - diff) + 15) * pixelsSmall + getBottomH;
            }else if(diff > 45){
                dotH.setRotation(BeginPosHead - 45);
                textChide.setText("Current: "+Integer.toString(45));
            }else if(diff < -45){
                dotH.setRotation(BeginPosHead + 45);
                textChide.setText("Current: "+Integer.toString(-45));
            }
        }else {
            if(diff <= 45 && diff >= -45) {
                PosHead3 = -((BeginPosHead - diff)+5) * 1;
                PositionHead = PosHead1 + PosHead2 + PosHead3;

                ((ViewGroup.MarginLayoutParams) dotH.getLayoutParams()).bottomMargin = PositionHead + getBottomH;
                dotH.setRotation(BeginPosHead - diff);

                textHhide.setText("Current: "+Integer.toString(diff));
            }else if(diff > 45){
                dotH.setRotation(BeginPosHead - 45);

                textChide.setText("Current: "+Integer.toString(45));
            }else if(diff < -45){
                dotH.setRotation(BeginPosHead + 45);

                textChide.setText("Current: "+Integer.toString(-45));
            }

        }

        if(sound.isChecked()){
            if(diff > range){
                SoundNumH++;
                if(SoundNumH == 1){
                    tg.startTone(ToneGenerator.TONE_PROP_ACK);
                }else if(SoundNumH > 1){
                    SoundNumH = 2;
                }
            }else if(diff <= range & diff >= -range){
                SoundNumH = 0;
            }else if(diff < -range){
                SoundNumH++;
                if(SoundNumH == 1){
                    tg.startTone(ToneGenerator.TONE_PROP_ACK);
                }else if(SoundNumH > 1){
                    SoundNumH = 2;
                }
            }
        }
    }

    public void setBaseline(){
        BaseM.requestLayout();
        ((ViewGroup.MarginLayoutParams)BaseM.getLayoutParams()).bottomMargin = getBottomM;
        ((ViewGroup.MarginLayoutParams)BaseM.getLayoutParams()).leftMargin = getLeftM;
        BaseM.requestLayout();
        BaseC.requestLayout();
        ((ViewGroup.MarginLayoutParams)BaseC.getLayoutParams()).bottomMargin = getBottomC;
        ((ViewGroup.MarginLayoutParams)BaseC.getLayoutParams()).leftMargin = getLeftC;
        BaseC.requestLayout();
        BaseH.requestLayout();
        ((ViewGroup.MarginLayoutParams)BaseH.getLayoutParams()).bottomMargin = getBottomH;
        ((ViewGroup.MarginLayoutParams)BaseH.getLayoutParams()).leftMargin = getLeftH;
        BaseH.requestLayout();
    }

    public void setReset(){
        dotM.requestLayout();
        ((ViewGroup.MarginLayoutParams)dotM.getLayoutParams()).bottomMargin = getBottomM;
        ((ViewGroup.MarginLayoutParams)dotM.getLayoutParams()).leftMargin = getLeftM;
        dotM.requestLayout();
        dotC.requestLayout();
        ((ViewGroup.MarginLayoutParams)dotC.getLayoutParams()).bottomMargin = getBottomC;
        ((ViewGroup.MarginLayoutParams)dotC.getLayoutParams()).leftMargin = getLeftC;
        dotC.requestLayout();
        dotH.requestLayout();
        ((ViewGroup.MarginLayoutParams)dotH.getLayoutParams()).bottomMargin = getBottomH;
        ((ViewGroup.MarginLayoutParams)dotH.getLayoutParams()).leftMargin = getLeftH;
        dotH.requestLayout();

        dotM.setRotation(BeginPos );
        dotC.setRotation(BeginPos );
        dotH.setRotation(BeginPosHead );

        //waarde wordt op null gezet als je start duwt
        beginValueM = Integer.parseInt(textM.getText().toString());
        beginValueC = Integer.parseInt(textC.getText().toString());
        beginValueH = Integer.parseInt(textH.getText().toString());

        PosHead1 = 0;
        PosHead2 = 0;
        PosHead3 = 0;
        PositionHead = 0;
        PosC1 = 0;
        PosC2 = 0;
        PositionC7 = 0;
    }

    public void setVisibilityRange(){
        RangeSimpleView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (RangeSimpleView.isChecked()) {
                    MaxM.setVisibility(View.VISIBLE);
                    MaxC.setVisibility(View.VISIBLE);
                    MaxH.setVisibility(View.VISIBLE);
                    MinM.setVisibility(View.VISIBLE);
                    MinC.setVisibility(View.VISIBLE);
                    MinH.setVisibility(View.VISIBLE);

                    BaseM.setAlpha((float) 0.3);
                    BaseC.setAlpha((float) 0.3);
                    BaseH.setAlpha((float) 0.3);
                } else {
                    MaxM.setVisibility(View.INVISIBLE);
                    MaxC.setVisibility(View.INVISIBLE);
                    MaxH.setVisibility(View.INVISIBLE);
                    MinM.setVisibility(View.INVISIBLE);
                    MinC.setVisibility(View.INVISIBLE);
                    MinH.setVisibility(View.INVISIBLE);

                    BaseM.setAlpha((float) 0.4);
                    BaseC.setAlpha((float) 0.4);
                    BaseH.setAlpha((float) 0.4);
                }
                setReset();
                setCurrentDataZero();
            }
        });
    }

    public void setRange(int range){
        MaxM.requestLayout();
        ((ViewGroup.MarginLayoutParams)MaxM.getLayoutParams()).bottomMargin = getBottomM + range * pixelsSmall;
        ((ViewGroup.MarginLayoutParams)MaxM.getLayoutParams()).leftMargin = getLeftM;
        MaxM.requestLayout();
        MaxC.requestLayout();
        ((ViewGroup.MarginLayoutParams)MaxC.getLayoutParams()).bottomMargin = getBottomC  + range * pixelsSmall;
        ((ViewGroup.MarginLayoutParams)MaxC.getLayoutParams()).leftMargin = getLeftC;
        MaxC.requestLayout();
        MaxH.requestLayout();
        ((ViewGroup.MarginLayoutParams)MaxH.getLayoutParams()).bottomMargin = getBottomH + range * pixelsSmall;
        ((ViewGroup.MarginLayoutParams)MaxH.getLayoutParams()).leftMargin = getLeftH;
        MaxH.requestLayout();

        MinM.requestLayout();
        ((ViewGroup.MarginLayoutParams)MinM.getLayoutParams()).bottomMargin = getBottomM - range * pixelsSmall;
        ((ViewGroup.MarginLayoutParams)MinM.getLayoutParams()).leftMargin = getLeftM ;
        MinM.requestLayout();
        MinC.requestLayout();
        ((ViewGroup.MarginLayoutParams)MinC.getLayoutParams()).bottomMargin = getBottomC - range * pixelsSmall;
        ((ViewGroup.MarginLayoutParams)MinC.getLayoutParams()).leftMargin = getLeftC;
        MinC.requestLayout();
        MinH.requestLayout();
        ((ViewGroup.MarginLayoutParams)MinH.getLayoutParams()).bottomMargin = getBottomH - range * pixelsSmall;
        ((ViewGroup.MarginLayoutParams)MinH.getLayoutParams()).leftMargin = getLeftH;
        MinH.requestLayout();

        MaxM.setRotation(BeginPos - (range * pixelsSmall));
        MaxC.setRotation(BeginPos - (range * pixelsSmall));
        MaxH.setRotation(BeginPosHead - range * pixelsSmall);
        MinM.setRotation(BeginPos + range * pixelsSmall);
        MinC.setRotation(BeginPos + range * pixelsSmall);
        MinH.setRotation(BeginPosHead + range * pixelsSmall);

        textMhideMax.setText("Max: "+(Integer.toString(range)));
        textMhideMin.setText("Min: -"+(Integer.toString(range)));

        textChideMax.setText("Max: "+(Integer.toString(range)));
        textChideMin.setText("Min: -"+(Integer.toString(range)));

        textHhideMax.setText("Max: "+(Integer.toString(range)));
        textHhideMin.setText("Min: -"+(Integer.toString(range)));
    }

    public void setCurrentDataZero(){
        textMhide.setText("Current: "+Integer.toString(0));
        textChide.setText("Current: "+Integer.toString(0));
        textHhide.setText("Current: "+Integer.toString(0));
    }

    public void setRotation(){
        dotM.setRotation(BeginPos );
        dotC.setRotation(BeginPos );
        dotH.setRotation(BeginPosHead );
        BaseM.setRotation(BeginPos );
        BaseC.setRotation(BeginPos );
        BaseH.setRotation(BeginPosHead );
    }

    public void  filesCheckbox(){
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

    public void save(View v) {

        if(toggle == 0){
            save.setText("Stop");
            message = "-";
            toggle = 1;
        }else if(toggle == 1){
            try {
                FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Saved to " + getFilesDir() + "/" + file_name,
                        Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            message = "-";
            save.setText("Start");
            toggle = 0;
        }

    }

    public void BackToMainMenu(View view){
        Intent intent = new Intent(RealTimeAnimation.this, MainActivity.class);
        startActivity(intent);
    }
}
