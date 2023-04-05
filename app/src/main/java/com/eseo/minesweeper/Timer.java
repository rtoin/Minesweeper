package com.eseo.minesweeper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class Timer extends Service {
    private Handler handler;

    public class MyBinder extends Binder {
        Timer getService() {
            return Timer.this;
        }
    }

    private final MyBinder myBinder = new MyBinder();

    public Timer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(GameActivity.BROADCAST);
                sendBroadcast(intent);
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable,1000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
