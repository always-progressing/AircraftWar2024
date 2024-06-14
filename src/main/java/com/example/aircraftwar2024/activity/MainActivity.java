package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.aircraftwar2024.ActivityManager;
import com.example.aircraftwar2024.R;
//import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    public static boolean isMusicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取“单机游戏”按钮
        Button singlePlayerButton = findViewById(R.id.singlePlayerButton);

        // 获取单选按钮
        RadioButton musicOnRadioButton = findViewById(R.id.musicOnRadioButton);
        RadioButton musicOffRadioButton = findViewById(R.id.musicOffRadioButton);

        // 设置按钮点击事件监听器
        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建Intent，跳转到OfflineActivity
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);

                // 获取音乐状态
               isMusicOn = musicOnRadioButton.isChecked();

                // 将音乐状态作为参数传递
                intent.putExtra("MUSIC_STATUS", isMusicOn);

                // 启动OfflineActivity
                startActivity(intent);
            }
        });
        // 设置首页Activity
        ActivityManager.getActivityManager().setHomeActivityClass(MainActivity.class);

        // 添加当前Activity到管理器
        ActivityManager.getActivityManager().addActivity(this);
    }
}
