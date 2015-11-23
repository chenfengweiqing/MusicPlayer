package com.canzhong.lcz.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class MainActivity extends Activity {
    private boolean isStartMusicList = false;   //判断是否启动列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startVideo();
            }
        }, 2000);
    }

    private void startVideo() {
        if (!isStartMusicList) {
            isStartMusicList = true;
            Intent intent = new Intent(this, MusicListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startVideo();
        return super.onTouchEvent(event);
    }
}