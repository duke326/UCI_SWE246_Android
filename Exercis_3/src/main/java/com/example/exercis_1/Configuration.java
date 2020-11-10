package com.example.exercis_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import static com.example.exercis_1.MutipleChoice.globalTimer;
import static com.example.exercis_1.MutipleChoice.timer;

public class Configuration extends AppCompatActivity {
    EditText timeLimit;

    Button traditionButton;
    Button imageButton;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        bindView();
        final Global global= (Global) this.getApplication();

        traditionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.setButtonType(true);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.setButtonType(false);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timeLimit.getText().toString().equals(null)){
                    global.setTimeLimit(Integer.parseInt(timeLimit.getText().toString()));
                }
            }
        });
    }

    private void bindView() {
        timeLimit=findViewById(R.id.TimeEditText);
        traditionButton=findViewById(R.id.traditionalButton);
        imageButton=findViewById(R.id.imageButton);
        confirm=findViewById(R.id.confrim);

    }

}