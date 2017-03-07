package com.example.macbookpro.task3_android2;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {


    ExecutorService es;

    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(2);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {


        String text = intent.getStringExtra("text");
        MyRun mr = new MyRun(startId, text);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {
        int startId;
        String text;
        public MyRun(int startId, String text) {
            this.text=text;
            this.startId = startId;
        }
        public void run() {
            Intent intent = new Intent(MainActivity.BROADCAST_ACTION);

            if(text.equals("hello"))
                intent.putExtra("request", " hello,Bro");
            else if(text.equals("bye"))intent.putExtra("request","stop");
            else intent.putExtra("request", "Alohomora,Old Man");
            int time = (int) (1 + (Math.random() * 3));
            try {
                Thread.sleep(time*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendBroadcast(intent);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(MyService.this)
                            .setSmallIcon(android.R.drawable.stat_notify_chat)
                            .setContentTitle("Notifications")
                            .setContentText("Service Lesson");
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0,mBuilder.build());
            stop();
        }
        void stop() {
        }
    }
}