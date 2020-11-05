package com.example.exercis_1;


import android.app.Application;

public class Score extends Application {
    private int score;
    public int getScore(){
        return this.score;
    }
    public void addScore(){
        this.score+=1;
    }

    @Override
    public void onCreate() {
        score=0;
        super.onCreate();
    }
}
