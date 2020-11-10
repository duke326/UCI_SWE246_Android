package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class FillInBlank extends AppCompatActivity {
    int chance=2;
    EditText answer;
    boolean running;
    int seconds=0;
    boolean activityRunning;
    int activitySecond=0;
    boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        running=true;
        activityRunning=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blank);
        runTimer();
        runActivityTimer();
        TextView question=findViewById(R.id.question);
        answer=findViewById(R.id.answer);
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answer.getText().toString().equalsIgnoreCase("spurs")){
                    chance--;

                    if(chance==0){
                        Intent intent=new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("chance",chance);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(),"you have "+chance+" chance, left",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("chance",chance);
                    startActivity(intent);
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

    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=activityRunning;
        activityRunning=false;
    }

    public void runTimer(){
        final TextView textView=findViewById(R.id.timer);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time=String.format(Locale.CHINA, "%d:%02d:%02d",hours,minutes,secs);
                textView.setText("timer B: "+time);
                if(running){
                    seconds++;
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
