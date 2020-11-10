package com.example.exercis_1;


import android.app.Application;

public class Global extends Application {
    private int score;
    boolean timeDisplay;
    boolean buttonType;
    int timeLimit;
    public int getScore(){
        return this.score;
    }
    public void addScore(){
        this.score+=1;
    }
    public boolean getTimeDisplay(){
        return this.timeDisplay;
    }

    public void setTimeDisplay(boolean res) {
        this.timeDisplay = res;
    }
    public boolean getButtonType(){
        return this.buttonType;
    }

    public void setButtonType(boolean res) {
        this.buttonType = res;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    @Override
    public void onCreate() {
        score=0;
        timeDisplay=true;
        buttonType=true;
        timeLimit=999999999;
        super.onCreate();
    }
}
