package com.canzhong.lcz.musicplayer;

/**
 * Created by LCZ on 2015-11-23.
 */
public class MusicItem {
    private String name ;
    private long duration ;
    private long size ;
    private String data ;

    public  MusicItem(String name,long duration,long size,String data ){
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.data = data;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                '}';
    }
}
