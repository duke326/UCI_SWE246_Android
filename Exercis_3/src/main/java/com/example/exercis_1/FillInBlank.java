package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

public class FillInBlank extends AppCompatActivity implements View.OnClickListener{
    int chance=2;
    EditText answer;
    boolean running;
    int seconds=0;
    boolean activityRunning;
    int activitySecond=0;
    boolean wasRunning;
    TextView timer;
    TextView globalTimer;
    Button submit;
    Button settingButton;
    Global global;
    ImageButton imageButton;
    ImageButton submitImageButton;
    ToggleButton timerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        running=true;
        activityRunning=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blank);
        global= (Global) this.getApplication();
        bindView();
        runTimer();
        runActivityTimer();
        imageButton.setVisibility(View.INVISIBLE);

    }

    private void bindView() {
        answer=findViewById(R.id.answer);
        submit=findViewById(R.id.submit);
        settingButton=findViewById(R.id.settingButton);
        imageButton=findViewById(R.id.imageButton);
        timerToggle=findViewById(R.id.timeToggleButton);
        submitImageButton=findViewById(R.id.submitimageButton);
        submit.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        submitImageButton.setOnClickListener(this);
        timerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b){
                        timer.setVisibility(View.INVISIBLE);
                        globalTimer.setVisibility(View.INVISIBLE);

                    }
                    else{
                        timer.setVisibility(View.VISIBLE);
                        globalTimer.setVisibility(View.VISIBLE);
                    }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(wasRunning){
            activityRunning=true;
        }
        if(global.getTimeDisplay()){
            timer.setVisibility(View.VISIBLE);
            globalTimer.setVisibility(View.VISIBLE);

        }
        else{
            timer.setVisibility(View.INVISIBLE);
            globalTimer.setVisibility(View.INVISIBLE);
        }
        if(global.getButtonType()){
            settingButton.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.VISIBLE);
            submitImageButton.setVisibility(View.INVISIBLE);

        }
        else{
            settingButton.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.VISIBLE);
            submit.setVisibility(View.INVISIBLE);
            submitImageButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=activityRunning;
        activityRunning=false;
    }

    public void runTimer(){
        timer=findViewById(R.id.timer);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(running){


                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time=String.format(Locale.CHINA, "%d:%02d:%02d",hours,minutes,secs);
                timer.setText("timer B: "+time);
                if(running){
                    seconds++;
                }
                if(seconds>global.getTimeLimit()){
                    running=false;
                    Intent intent=new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("chance",0);
                    startActivity(intent);
                }
                handler.postDelayed(this,1000);
                }
            }
        });

    }
    public void runActivityTimer(){
        globalTimer=findViewById(R.id.activity_timer);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=activitySecond/3600;
                int minutes=(activitySecond%3600)/60;
                int secs=activitySecond%60;
                String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);
                globalTimer.setText("timer A： "+time);
                if(activityRunning){
                    activitySecond++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.submit:
                if(!answer.getText().toString().equalsIgnoreCase("spurs")){
                    chance--;

                    if(chance==0){
                        intent=new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("chance",chance);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(),"you have "+chance+" chance, left",Toast.LENGTH_SHORT).show();
                }
                else{
                    intent=new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("chance",chance);
                    startActivity(intent);
                }
                break;
            case R.id.settingButton:
               intent=new Intent(getApplicationContext(), Configuration.class);
                startActivity(intent);
                break;
            case R.id.imageButton:
                intent=new Intent(getApplicationContext(), Configuration.class);
                startActivity(intent);
                break;
            case R.id.submitimageButton:
                if(!answer.getText().toString().equalsIgnoreCase("spurs")){
                    chance--;

                    if(chance==0){
                        intent=new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("chance",chance);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(),"you have "+chance+" chance, left",Toast.LENGTH_SHORT).show();
                }
                else{
                    intent=new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("chance",chance);
                    startActivity(intent);
                }
                break;

        }
    }
}
