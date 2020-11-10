package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FillInBlank extends AppCompatActivity {

    //chance
    int chance=2;
    EditText answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blank);
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
}
