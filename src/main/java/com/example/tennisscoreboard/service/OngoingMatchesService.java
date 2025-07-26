package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.exception.NotFoundException;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.model.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private static final OngoingMatchesService instance = new OngoingMatchesService();


    public static OngoingMatchesService getInstance() {
        return instance;
    }

    private final PlayerPersistenceService playerPersistenceService = new PlayerPersistenceService();


    private final Map<UUID, MatchScore> matches = new ConcurrentHashMap<>();


    private OngoingMatchesService() {
    }

    public UUID createMatch(String playerFirsName, String playerSecondName) {

        Player player1 = playerPersistenceService.getOrCreate(playerFirsName);
        Player player2 = playerPersistenceService.getOrCreate(playerSecondName);
        MatchScore matchScore = new MatchScore(player1.getName(), player2.getName());
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, matchScore);
        return uuid;
    }


    public MatchScore getMatchScore(UUID uuid) {
        return Optional.ofNullable(matches.get(uuid))
                .orElseThrow(() -> new NotFoundException("Match with UUID=" +uuid + " not found"));
    }


    public void removeMatch(UUID uuid) {
        matches.remove(uuid);
    }


}