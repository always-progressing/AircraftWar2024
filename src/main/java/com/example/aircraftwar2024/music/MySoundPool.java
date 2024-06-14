package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.aircraftwar2024.R;

public class MySoundPool {
    private SoundPool soundPool;
    private int bulletHitSound;
    private int bombExplosionSound;
    private int getSupplySound;
    private int gameOverSound;

    public MySoundPool(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();

        bulletHitSound = soundPool.load(context, R.raw.bullet_hit, 1);
        bombExplosionSound = soundPool.load(context, R.raw.bomb_explosion, 1);
        getSupplySound = soundPool.load(context, R.raw.get_supply, 1);
        gameOverSound = soundPool.load(context, R.raw.game_over, 1);
    }

    public void playBulletHit() {
        soundPool.play(bulletHitSound, 1, 1, 1, 0, 1);
    }

    public void playBombExplosion() {
        soundPool.play(bombExplosionSound, 1, 1, 1, 0, 1);
    }

    public void playGetSupply() {
        soundPool.play(getSupplySound, 1, 1, 1, 0, 1);
    }

    public void playGameOver() {
        soundPool.play(gameOverSound, 1, 1, 1, 0, 1);
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}