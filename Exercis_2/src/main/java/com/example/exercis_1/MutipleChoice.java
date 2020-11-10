package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.exercis_1.R.*;
import static com.example.exercis_1.R.array.*;

public class MutipleChoice extends AppCompatActivity {
    int chance;
    int defaultValue=0;
    boolean running;
    int second=0;
    boolean activityRunning;
    int activitySecond=0;
    boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        running=true;
        activityRunning=true;
        setContentView(layout.activity_mutiple_choice);
        Spinner spinner =findViewById(id.spinner);

        //set Timmer

        runTimer();
        runActivityTimer();

        chance=getResources().getStringArray(answers).length-3;
        String[] answers=getResources().getStringArray(array.answers);
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,answers);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] answers=getResources().getStringArray(array.answers);
                if(position==defaultValue){
                    defaultValue=-1;
                    return;
                }
                if (position!=2){
                    chance--;
                    Toast.makeText(getApplicationContext(), "you have "+chance+" left",Toast.LENGTH_SHORT).show();

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
    }

    public void runTimer(){
        final TextView textView=findViewById(id.timer);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=second/3600;
                int minutes=(second%3600)/60;
                int secs=second%60;
                String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);
                textView.setText("timer B: "+time);
                if(running){
                    second++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
    public void runActivityTimer(){
        final TextView globalTimer=findViewById(R.id.activity_timer);
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
