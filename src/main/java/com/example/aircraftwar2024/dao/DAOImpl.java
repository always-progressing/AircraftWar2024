package com.example.aircraftwar2024.dao;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DAOImpl implements DAO {
    private static String[] fileName = {"scores.txt", "scores_mob.txt", "scores_hard.txt"};
    private String scoreFile;
    private Context context;

    public DAOImpl(Context context, int gameType) {
        this.context = context;
        this.scoreFile = fileName[gameType - 1];
    }

    @Override
    public ArrayList<PlayerData> getAll() {
        ArrayList<PlayerData> playerScores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(scoreFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                String saveTime = parts[2];
                playerScores.add(new PlayerData(score, saveTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 按照得分降序排序
        Collections.sort(playerScores, Comparator.comparingInt(PlayerData::getScore).reversed());
        return playerScores;
    }

    @Override
    public void doAdd(PlayerData pd) {
        try (FileOutputStream fos = context.openFileOutput(scoreFile, Context.MODE_APPEND);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
             PrintWriter printWriter = new PrintWriter(writer)) {
            printWriter.println(pd.name + "," + pd.score + "," + pd.saveTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doDelete(String saveTime) {
        List<PlayerData> playerScores = getAll();
        Iterator<PlayerData> iterator = playerScores.iterator();
        while (iterator.hasNext()) {
            PlayerData playerData = iterator.next();
            if (playerData.saveTime.equals(saveTime)) {
                iterator.remove();
                break; // 如果只想删除一个记录，可以添加这个 break
            }
        }
        writeScoresToFile(playerScores);
    }

    // 将得分记录写回文件
    private void writeScoresToFile(List<PlayerData> playerScores) {
        try (FileOutputStream fos = context.openFileOutput(scoreFile, Context.MODE_PRIVATE);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
             PrintWriter printWriter = new PrintWriter(writer)) {
            for (PlayerData playerData : playerScores) {
                printWriter.println(playerData.name + "," + playerData.score + "," + playerData.saveTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayLeaderboard(List<PlayerData> playerScores) {
        for (int i = 0; i < playerScores.size(); i++) {
            PlayerData playerData = playerScores.get(i);
            System.out.println("第" + (i + 1) + "名" + ":" + playerData.name + "," + playerData.score + "," + playerData.saveTime);
        }
    }
}
