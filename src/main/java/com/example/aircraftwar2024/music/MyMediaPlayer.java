package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;

public class MyMediaPlayer {
    private MediaPlayer bgmPlayer;
    private MediaPlayer bossBgmPlayer;
    private Context context;

    public MyMediaPlayer(Context context) {
        this.context = context;
        bgmPlayer = MediaPlayer.create(context, R.raw.bgm);
        bossBgmPlayer = MediaPlayer.create(context, R.raw.bgm_boss);
    }

    public void playBgm() {
        if (bgmPlayer != null) {
            bgmPlayer.setLooping(true);
            bgmPlayer.start();
        }
    }

    public void stopBgm() {
        if (bgmPlayer != null && bgmPlayer.isPlaying()) {
            bgmPlayer.pause();
        }
    }

    public void playBossBgm() {
        if (bossBgmPlayer != null) {
            bossBgmPlayer.setLooping(true);
            bossBgmPlayer.start();
        }
    }

    public void stopBossBgm() {
        if (bossBgmPlayer != null && bossBgmPlayer.isPlaying()) {
            bossBgmPlayer.pause();
        }
    }

    public void release() {
        if (bgmPlayer != null) {
            bgmPlayer.release();
            bgmPlayer = null;
        }
        if (bossBgmPlayer != null) {
            bossBgmPlayer.release();
            bossBgmPlayer = null;
        }
    }
}