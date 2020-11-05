package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Score score= (Score) this.getApplicationContext();
        setContentView(R.layout.activity_result);
        Intent intent=getIntent();

        int chance=intent.getIntExtra("chance",-1);
        if(chance==0){
            TextView res=findViewById(R.id.fail);
            res.setVisibility(res.VISIBLE);
        }
        else{
            TextView res=findViewById(R.id.pass);
            res.setVisibility(res.VISIBLE);

            score.addScore();
        }
        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        Button share=findViewById(R.id.share);
        final int finalScore=score.getScore();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_TEXT,"You pass "+finalScore+" quizs");
                intent.setType("text/plain");
                startActivity(intent);
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_CHOOSER);
                intent2.putExtra(Intent.EXTRA_TITLE, "please selete a app");
                intent2.putExtra(Intent.EXTRA_INTENT, intent);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), "Score has sent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
