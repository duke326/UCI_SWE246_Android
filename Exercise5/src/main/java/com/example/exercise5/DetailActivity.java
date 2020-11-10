package com.example.exercise5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int id=getIntent().getIntExtra(EXTRA,0);
        DetailFragment deatilFragment= (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        deatilFragment.setTaskId(id);
    }
}