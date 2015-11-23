package com.canzhong.lcz.musicplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
/**
 * Created by LCZ on 2015-11-23.
 */
public class PlayMusicActivity extends Activity implements View.OnClickListener{
    private Button mPlay;
    private Button mStop;
    private Button mPause;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play_layout);
        mPlay = (Button) findViewById(R.id.play);
        mStop = (Button) findViewById(R.id.stop);
        mPause = (Button) findViewById(R.id.pause);
        uri = getIntent().getData();
        Log.d("lcz","--------uri---"+uri+"-----");
        initMediaPlayer(uri); // 初始化MediaPlayer
        mPlay.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mPause.setOnClickListener(this);
    }
    private void initMediaPlayer(Uri uri) {
        try {
            Log.d("lcz","-------initMediaPlayer-----");
            mediaPlayer.setDataSource(PlayMusicActivity.this,uri); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play :
                if (!mediaPlayer.isPlaying()) {
                    Log.d("lcz","--------play-----");
                    mediaPlayer.start(); // 开始播放
                }
                break;
            case R.id.stop :
                if (mediaPlayer.isPlaying()) {
                    Log.d("lcz","--------stop-----");
                    mediaPlayer.reset(); // 停止播放
                    initMediaPlayer(uri);
                }
                break;
            case R.id.pause :
                if (mediaPlayer.isPlaying()) {
                    Log.d("lcz","--------pause-----");
                    mediaPlayer.pause(); // 暂停播放
                }
                break;
            default :
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
