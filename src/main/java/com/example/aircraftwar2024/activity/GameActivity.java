package com.example.aircraftwar2024.activity;

import static com.example.aircraftwar2024.game.BaseGame.getScore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    public static int gameType = 0;
    public static int screenWidth, screenHeight;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getScreenHW();
        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    showGameOverScreen();
                }
            }
        };

        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
            Log.i(TAG, "Received gameType: " + gameType);
        }

        BaseGame baseGameView = null;
        switch (gameType) {
            case 1:
                baseGameView = new EasyGame(this, mainHandler);
                Log.i(TAG, "Initializing EasyGame");
                break;
            case 2:
                baseGameView = new MediumGame(this, mainHandler);
                Log.i(TAG, "Initializing MediumGame");
                break;
            case 3:
                baseGameView = new HardGame(this, mainHandler);
                Log.i(TAG, "Initializing HardGame");
                break;
            default:
                baseGameView = new EasyGame(this, mainHandler); // Default to easy mode
                Log.i(TAG, "Initializing Default EasyGame");
                break;
        }
        if (baseGameView != null) {
            baseGameView.action();
            setContentView(baseGameView);
        } else {
            Log.e(TAG, "baseGameView is null");
        }
    }

    public void getScreenHW() {
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay().getRealMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    private void showGameOverScreen() {
        Date now = new Date();

        // 创建一个格式化对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化当前时间
        String formattedDate = sdf.format(now);
        Log.i(TAG, "showGameOverScreen ready");
        Intent intent = new Intent(GameActivity.this, RecordActivity.class);
        intent.putExtra("gameType", gameType);
        intent.putExtra("score",getScore());
        intent.putExtra("time",formattedDate);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
