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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        running=true;
        setContentView(layout.activity_mutiple_choice);
        setTimer();
        Spinner spinner =findViewById(id.spinner);
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
    public void setTimer(){
        final TextView textView=findViewById(id.timer);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=second/3600;
                int minutes=(second/3600)%60;
                int secs=second%60;
                String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);
                textView.setText("time: "+time);
                if(running){
                    second++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
}
