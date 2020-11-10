package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

import static com.example.exercis_1.R.*;
import static com.example.exercis_1.R.array.*;

public class MutipleChoice extends AppCompatActivity implements View.OnClickListener{
    int chance;
    int defaultValue=0;

    boolean running;
    int second=0;
    boolean activityRunning;
    int activitySecond=0;
    boolean wasRunning;
     static TextView timer;
    static TextView globalTimer;
    public static MutipleChoice instance = null;
    Spinner spinner;
    Spinner spinner2;
    Button settingButton;
    Global global;//global variant
    ImageButton imageButton;
    ToggleButton timerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        running=true;
        activityRunning=true;
        setContentView(layout.activity_mutiple_choice);
        bindView();
        setTimer();
        runActivityTimer();
        global= (Global) this.getApplication();

        chance=getResources().getStringArray(answers).length-3;
        String[] answers=getResources().getStringArray(array.answers);
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,answers);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] answers=getResources().getStringArray(array.answers);
                if(position==0){

                }
                else if (position!=2){
                    //Toast.makeText(getApplicationContext(), "you click "+answers[position],Toast.LENGTH_SHORT).show();
                    chance--;
                    if(chance==0){
                        Intent intent=new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("chance", chance);
                        
                        startActivity(intent);
                    }
                }
                else{
                    Intent intent=new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("chance", chance);
                    startActivity(intent);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String > spinner2Adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, answers);
        spinner2.setAdapter(spinner2Adapter);
         spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] answers=getResources().getStringArray(array.answers);
                if(position==0){}
                else if (position!=2){
                    Toast.makeText(getApplicationContext(), "you click "+answers[position],Toast.LENGTH_SHORT).show();
                    chance--;
                    if(chance==0){
                        Intent intent=new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("chance", chance);

                        startActivity(intent);
                    }
                }
                else{
                    Intent intent=new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("chance", chance);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private  void bindView(){
        spinner=findViewById(R.id.spinner);
        spinner2=findViewById(R.id.spinner2);
        settingButton=findViewById(R.id.settingButton);
        imageButton=findViewById(R.id.imageButton);
        settingButton.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        timerToggle=findViewById(id.timeToggleButton);
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
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case id.settingButton:
                intent=new Intent(getApplicationContext(), Configuration.class);
                startActivity(intent);
                break;
            case id.imageButton:
                intent=new Intent(getApplicationContext(), Configuration.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=activityRunning;
        activityRunning=false;
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
        }
        else{
            settingButton.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.VISIBLE);
        }

    }

    public void setTimer(){
        timer=findViewById(id.timer);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    int hours=second/3600;
                    int minutes=(second%3600)/60;
                    int secs=second%60;
                    String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);
                    timer.setText("time B: "+time);
                    if(running){
                        second++;
                    }
                    if(second>global.getTimeLimit()){
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

}
