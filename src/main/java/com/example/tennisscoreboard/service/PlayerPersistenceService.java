package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.dao.PlayerDao;
import com.example.tennisscoreboard.model.Player;

public class PlayerPersistenceService {

    private final PlayerDao playerDao = new PlayerDao();

    public Player getOrCreate(String name) {
        return playerDao.findPlayerByName(name)
                .orElseGet(() -> {
                    Player newPlayer = new Player(name);
                    playerDao.createPlayer(newPlayer);
                    return newPlayer;
                });
    }
}
