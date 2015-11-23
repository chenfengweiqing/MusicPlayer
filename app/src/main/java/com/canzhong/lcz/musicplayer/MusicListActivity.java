package com.canzhong.lcz.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LCZ on 2015-11-23.
 */
public class MusicListActivity extends Activity {
    private ListView mMusic_List;
    private TextView mNo_Music;
    private ArrayList<MusicItem> mMusicList;
    private Utils util;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mMusicList!=null&&mMusicList.size()>0){
                mNo_Music.setVisibility(View.GONE);
                mMusic_List.setAdapter(new MusicListAdapter());
            }else{
                mNo_Music.setVisibility(View.VISIBLE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list_layout);
        initView();
        getData();
        setListener();
    }
    /**
     * 初始化
     */
    private void initView(){
        util = new Utils();
        mMusic_List = (ListView) findViewById(R.id.video_list);
        mNo_Music = (TextView) findViewById(R.id.there_no_video);
    }
    /**
     * 设置监听
     */

    private void setListener(){
        mMusic_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicItem musicItem = mMusicList.get(position);
                Intent intent = new Intent(MusicListActivity.this,PlayMusicActivity.class);
                intent.setData(Uri.parse(musicItem.getData()));
                startActivity(intent);
            }
        });
    }

    /**
     * 获取数据
     */
    class MusicListAdapter extends BaseAdapter {

        public MusicListAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return mMusicList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            MusicItem video = mMusicList.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                view =  View.inflate(MusicListActivity.this,R.layout.music_item_layout,null);
                viewHolder = new ViewHolder();
                viewHolder.music_item_name = (TextView) view.findViewById(R.id.music_item_name);
                viewHolder.music_item_duration = (TextView) view.findViewById(R.id.music_item_duration);
                viewHolder.music_item_size = (TextView) view.findViewById(R.id.music_item_size);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.music_item_name.setText(video.getName());
            viewHolder.music_item_duration.setText(util.stringForTime(((int) video.getDuration())));
            viewHolder. music_item_size.setText(Formatter.formatFileSize(MusicListActivity.this, video.getSize()));  //转换为对应的大小
            return view;
        }
        class ViewHolder {
            TextView music_item_name;
            TextView music_item_duration;
            TextView music_item_size;
        }
    }

    private void getData(){
        new Thread(){
            public void run(){
                //读取手机的视频
                mMusicList = new ArrayList<MusicItem>();
//                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String [] projection = {
                        MediaStore.Audio.Media.DISPLAY_NAME,  //视频名称
                        MediaStore.Audio.Media.DURATION,    //视频时长
                        MediaStore.Audio.Media.SIZE,      //视频大小
                        MediaStore.Audio.Media.DATA      //视频在SD卡下的绝对路径
                };
                Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                while (cursor.moveToNext()){
                    String name = cursor.getString(0);
                    long duration = cursor.getLong(1);
                    long size = cursor.getLong(2);
                    String data = cursor.getString(3);
                    MusicItem musicItem = new MusicItem(name,duration,size,data);
                    mMusicList.add(musicItem);
                }
                cursor.close();
                handler.sendEmptyMessage(1);
            }
        }.start();
    }
}
