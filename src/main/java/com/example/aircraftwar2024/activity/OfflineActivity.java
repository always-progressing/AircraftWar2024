package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
//import com.example.myapplication.R;

public class OfflineActivity extends AppCompatActivity {

    public enum GameMode{
        /**
         * 简单、普通、困难三种模式
         */
        EASY,NORMAL,HARD;
        public String getFileName(){
            switch (this){
                case EASY:return "EasyGameRecords.dat";
                case NORMAL:return "NormalGameRecords.dat";
                default:return "HardGameRecords.dat";
            }
        }
    }
    public volatile static GameMode gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        // 获取Intent和传递的参数
        Intent intent = getIntent();
        boolean isMusicOn = intent.getBooleanExtra("MUSIC_STATUS", false);

        // 显示音乐状态
        String musicStatus = isMusicOn ? "开启音乐" : "关闭音乐";
        Toast.makeText(this, "音乐状态: " + musicStatus, Toast.LENGTH_SHORT).show();

        // 设置文本框内容
        TextView textView = findViewById(R.id.textView);
        textView.setText("单机模式难度选择");

        // 设置按钮点击事件
        Button easyModeButton = findViewById(R.id.easyModeButton);
        Button normalModeButton = findViewById(R.id.normalModeButton);
        Button hardModeButton = findViewById(R.id.hardModeButton);

        // 设置按钮的点击事件逻辑
        easyModeButton.setOnClickListener(v -> {
            gameMode = OfflineActivity.GameMode.EASY;
            startGameActivity(1);
        }); // 1 represents easy mode
        normalModeButton.setOnClickListener(v -> {
            gameMode = OfflineActivity.GameMode.NORMAL;
            startGameActivity(2);
        }); // 2 represents normal mode
        hardModeButton.setOnClickListener(v -> {
            gameMode = OfflineActivity.GameMode.HARD;
            startGameActivity(3);
        }); // 3 represents hard mode
        }
    private void startGameActivity(int gameType) {
        Intent gameIntent = new Intent(OfflineActivity.this, GameActivity.class);
        gameIntent.putExtra("gameType", gameType);
        startActivity(gameIntent);
    }

}
