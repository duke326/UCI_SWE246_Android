package com.example.exercis_1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Locale;
import java.util.Random;

public class TimerBindService extends Service {
    Random random = new Random();;
    static int globalSecond=0;
    static int activitySecond=0;
   static  boolean globalRunning;
   static  boolean activityRunning;
   static boolean activityWasRunning;
    Global global;
    public static final int NOTIFICATION_ID = 5453;
    public TimerBindService() {
    }
    public class MyBinder extends Binder{
        public TimerBindService getService(){

            return TimerBindService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        global= (Global) this.getApplication();
        globalRunning=true;
        activityRunning=true;
        return new MyBinder();
    }
    public void runGlobalTimer(){

                        int hours=globalSecond/3600;
                        int minutes=(globalSecond%3600)/60;
                        int secs=globalSecond%60;
                        String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);

                        if(globalRunning&&globalSecond<global.getTimeLimit()){
                            globalSecond++;
                            //Toast.makeText(getApplicationContext(), "this is "+globalSecond, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Time up", Toast.LENGTH_SHORT).show();
                            globalRunning=false;
                            showNotification("TIME UP");
                        }


                }
    public void runActivityTimer(){

        //globalTimer=findViewById(R.id.activity_timer);
            int hours=activitySecond/3600;
            int minutes=(activitySecond%3600)/60;
            int secs=activitySecond%60;
            String time= String.format(Locale.CHINA, "%d:%02d:%02d", hours, minutes, secs);

            if(activityRunning&&activitySecond<global.getTimeLimit()){
                activitySecond++;
                //Toast.makeText(getApplicationContext(), "this is "+activitySecond, Toast.LENGTH_SHORT).show();
            }

        }




    public void showNotification(final String  text){

        String id = "my_channel_01";
        String name="Channel name";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            //Toast.makeText(this, mChannel.toString(), Toast.LENGTH_SHORT).show();
            //Log.d(, mChannel.toString());
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this)
                    .setChannelId(id)
                    .setContentTitle("Time UP")
                    .setContentText("You fail this quiz")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        }
        notificationManager.notify(111123, notification);
    }


}
