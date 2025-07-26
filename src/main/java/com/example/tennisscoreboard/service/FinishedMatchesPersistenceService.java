package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.dao.MatchDao;
import com.example.tennisscoreboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinishedMatchesPersistenceService {

    private static final Logger log = LoggerFactory.getLogger(FinishedMatchesPersistenceService.class);

    private final MatchDao dao = new MatchDao();

    public void save(Match match) {
        if (match == null) {
            log.warn("Trying to persist a null match object");
            return;
        }

        dao.save(match);
        log.info("Match {} has been saved to the database", match);
    }
}
