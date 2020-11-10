package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
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
//     static TextView timer;
//    static TextView globalTimer;
    public static MutipleChoice instance = null;
    Spinner spinner;
    Spinner spinner2;
    Button settingButton;
    Global global;//global variant
    ImageButton imageButton;
    TimerBindService timerBindService;
    boolean serviceTimerRunning;
    boolean temp;
    //new Code: set the ServiceConnection
    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            timerBindService=((TimerBindService.MyBinder)iBinder).getService();

        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        running=true;
        activityRunning=true;
        serviceTimerRunning=true;
        temp=true;
        setContentView(layout.activity_mutiple_choice);
        bindView();
//        setTimer();
//        runActivityTimer();
        //Exercise 4 : start timeService
        //new Code StartTimerService
        TimerBindService.globalRunning=true;
        TimerBindService.activityRunning=true;
        startActivityTimerService();
        startGlobalTimerService();
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
    
    //server

    @Override
    protected void onStart() {
        super.onStart();
        //new Code :bind in the onstart method
        Intent intent=new Intent(getApplicationContext(), TimerBindService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //new Code: unbind
        unbindService(serviceConnection);
        TimerBindService.activityRunning=false;
    }

    private  void bindView(){
        spinner=findViewById(R.id.spinner);
        spinner2=findViewById(R.id.spinner2);
        settingButton=findViewById(R.id.settingButton);
        imageButton=findViewById(R.id.imageButton);
        settingButton.setOnClickListener(this);
        imageButton.setOnClickListener(this);
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
        //services:
        TimerBindService.activityWasRunning=TimerBindService.activityRunning;
        TimerBindService.activityRunning=false;
    }

    @Override
    protected void onResume() {

        super.onResume();
        //timerBindService.runActivityTimer();
        if(wasRunning){
            activityRunning=true;
        }
        //set timer visibility
//        if(global.getTimeDisplay()){
//            timer.setVisibility(View.VISIBLE);
//            globalTimer.setVisibility(View.VISIBLE);
//
//        }
//        else{
//            timer.setVisibility(View.INVISIBLE);
//            globalTimer.setVisibility(View.INVISIBLE);
//        }

        //set button type
        if(global.getButtonType()){
            settingButton.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
        }
        else{
            settingButton.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.VISIBLE);
        }
        //service

//      new Code :on resume
        if(TimerBindService.activityWasRunning){
            TimerBindService.activityRunning=true;
        }

    }
    //new Code :onDestory: set services to original
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimerBindService.globalRunning=false;
        TimerBindService.globalSecond=0;
        TimerBindService.activityRunning=false;
        TimerBindService.activitySecond=0;
        serviceTimerRunning=false;
    }

//new Code: handler submit global timer and activity time
    public void startGlobalTimerService(){
        final Handler handler=new Handler();

            handler.post(new Runnable() {
                public void run() {
                    if (TimerBindService.globalRunning) {
                        if (timerBindService != null && serviceTimerRunning) {
                            timerBindService.runGlobalTimer();
                            Log.d("Timer", "this is Global time" + TimerBindService.globalSecond);
                        }
                        handler.postDelayed(this, 1000);
                    }
                    else{
                        Intent intent=new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("chance",0);
                        startActivity(intent);
                    }
                }
            });
        }


    public void startActivityTimerService(){
        final Handler handler=new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (TimerBindService.activityRunning) {
                    if (timerBindService != null && serviceTimerRunning) {
                        timerBindService.runActivityTimer();
                        Log.d("Timer", "this is Activity time" + TimerBindService.activitySecond);
                    }

                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

//    public void setTimer(){
//        timer=findViewById(id.timer);
//        final Handler handler=new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if(running){
//                    int hours=second/3600;
//                    int minutes=(second%3600)/60;
//                    int secs=second%60;
//                    String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);
//                    timer.setText("time B: "+time);
//                    if(running){
//                        second++;
//                    }
//                    //fail the quiz
////                    if(second>global.getTimeLimit()){
////                        running=false;
////                        Intent intent=new Intent(getApplicationContext(), Result.class);
////                        intent.putExtra("chance",0);
////                        startActivity(intent);
////                    }
//
//                    handler.postDelayed(this,1000);
//                }
//
//            }
//        });
//    }
//    public void runActivityTimer(){
//        globalTimer=findViewById(R.id.activity_timer);
//        final Handler handler=new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                int hours=activitySecond/3600;
//                int minutes=(activitySecond%3600)/60;
//                int secs=activitySecond%60;
//                String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);
//                globalTimer.setText("timer A： "+time);
//                if(activityRunning){
//                    activitySecond++;
//                }
//                handler.postDelayed(this,1000);
//            }
//        });
//    }
}
