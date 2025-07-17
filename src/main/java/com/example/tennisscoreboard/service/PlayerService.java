package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.dao.PlayerDao;
import com.example.tennisscoreboard.model.Player;

public class PlayerService {

    private final PlayerDao playerDao = new PlayerDao();

    public Player getOrCreatePlayer(String name) {
        return playerDao.getPlayerByName(name)
                .orElseGet(() -> {
                    Player newPlayer = new Player(name);
                    playerDao.createPlayer(newPlayer);
                    return newPlayer;
                });
    }
}
