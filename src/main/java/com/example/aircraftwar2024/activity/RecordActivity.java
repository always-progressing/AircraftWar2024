package com.example.aircraftwar2024.activity;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.ActivityManager;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.dao.DAOImpl;
import com.example.aircraftwar2024.dao.PlayerData;
import com.example.aircraftwar2024.game.BaseGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String, String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Log.i(TAG, "enter");
        // 添加当前Activity到管理器
        ActivityManager.getActivityManager().addActivity(this);

        // 设置返回首页按钮
        Button returnHomeButton = findViewById(R.id.return_home_button);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回首页
                BaseGame.score = 0;
                ActivityManager.getActivityManager().returnToHome(RecordActivity.this);
            }
        });

        listView = findViewById(R.id.lv_records);
        TextView modeTextView = findViewById(R.id.tv_mode);

        // 假设你从 Intent 中获取模式参数
        int mode = GameActivity.gameType;
        String modeName;
        switch (mode) {
            case 1:
                modeName = "简单模式";
                break;
            case 2:
                modeName = "中等模式";
                break;
            case 3:
                modeName = "困难模式";
                break;
            default:
                modeName = "未知模式";
                break;
        }
        modeTextView.setText(modeName);

        // 获取数据
        DAOImpl dao = new DAOImpl(this, mode);
        int score = getIntent().getIntExtra("score",0);
        String time = getIntent().getStringExtra("time");
        PlayerData playerData = new PlayerData(score,time);
        dao.doAdd(playerData);
        loadData(dao);

        // 添加单击监听
        // 添加单击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> selectedRecord = (Map<String, String>) parent.getItemAtPosition(position);
                showDeleteConfirmationDialog(selectedRecord,dao);
            }
        });
    }
    private void loadData(DAOImpl dao){
        List<PlayerData> playerDataList = dao.getAll();

        // 准备数据
        data = new ArrayList<>();
        for (int i = 0; i < playerDataList.size(); i++) {
            PlayerData pd = playerDataList.get(i);
            Map<String, String> map = new HashMap<>();
            map.put("rank", String.valueOf(i + 1));
            map.put("username", pd.name);
            map.put("score", String.valueOf(pd.score));
            map.put("time", pd.saveTime);
            data.add(map);
        }
        Log.i(TAG, "data ready");

        // 创建适配器
        adapter = new SimpleAdapter(
                this,
                data,
                R.layout.activity_item,
                new String[]{"rank", "username", "score", "time"},
                new int[]{R.id.tv_rank, R.id.tv_username, R.id.tv_score, R.id.tv_time}
        );

        // 绑定适配器到ListView
        listView.setAdapter(adapter);
    }
    private void showDeleteConfirmationDialog(Map<String, String> record,DAOImpl dao) {
        String username = record.get("username");
        String saveTime = record.get("time");

        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("你确定要删除这条记录吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dao.doDelete( saveTime);
                        loadData(dao); // 更新视图
                        Toast.makeText(RecordActivity.this, "记录已删除", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
