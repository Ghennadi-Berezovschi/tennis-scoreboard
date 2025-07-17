package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.dao.MatchDao;
import com.example.tennisscoreboard.model.Match;

public class FinishedMatchesPersistenceService {

    private final MatchDao matchDao = new MatchDao();

    /**
     * Saves a finished match to the database.
     *
     * @param match the finished match to save
     */
    public void saveFinishedMatch(Match match) {
        if (match != null) {
            matchDao.save(match);
            System.out.println("Match saved to DB: " + match);
        } else {
            System.out.println("Cannot save null match");
        }
    }
}
