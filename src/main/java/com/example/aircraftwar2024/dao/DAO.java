package com.example.aircraftwar2024.dao;

import java.util.List;

public interface DAO {
    public List<PlayerData> getAll();


    public void doAdd(PlayerData pd);
//    public void doDelete(PlayerData pd);
}
