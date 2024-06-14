package com.example.aircraftwar2024.dao;

public class PlayerData {
    public String name = "test";
    public int score;
    public String saveTime;
    public PlayerData(int score,String saveTime){
        this.score=score;
        this.saveTime=saveTime;
    }
    public int getScore() {
        return score;
    }
}
