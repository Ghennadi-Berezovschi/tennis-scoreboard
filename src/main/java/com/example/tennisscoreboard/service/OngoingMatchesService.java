package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.exception.NotFoundException;
import com.example.tennisscoreboard.model.MatchScore;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private static final OngoingMatchesService instance = new OngoingMatchesService();

    //Singleton instance;
    public static OngoingMatchesService getInstance() {
        return instance;
    }

    private final Map<UUID, MatchScore> matches = new ConcurrentHashMap<>();

    //  private constructor to prevent creating new instances
    private OngoingMatchesService() {}

    public UUID createMatch(MatchScore matchScore ) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, matchScore);
        return uuid;
    }
    public MatchScore getMatchScoreOrThrow(UUID uuid) {
        MatchScore match = matches.get(uuid);
        if (match == null) {
            throw new NotFoundException("Match not found for UUID: " + uuid);
        }
        return match;
    }


    public void removeMatch(UUID uuid) {
        matches.remove(uuid);
    }



}