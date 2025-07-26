package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.dao.MatchDao;
import com.example.tennisscoreboard.dto.MatchResponseDto;
import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.mapper.MatchMapper;
import com.example.tennisscoreboard.model.Match;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.model.Player;

import java.util.List;
import java.util.UUID;

public class MatchService {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private final PlayerPersistenceService playerPersistenceService = new PlayerPersistenceService();
    private final MatchDao matchDao = new MatchDao();

    public List<Match> getMatches(int page, int pageSize, String filteredPlayerName) {
        if (filteredPlayerName != null) {
            return matchDao.findMatchesByPlayerNamePaged(filteredPlayerName, page, pageSize);
        } else {
            return matchDao.findMatchesPaged(page, pageSize);
        }

    }

    public long getTotalMatches(String filteredPlayerName) {
        if (filteredPlayerName != null) {
            return matchDao.countMatchesByPlayerName(filteredPlayerName);

        } else {
            return matchDao.countAllMatches();
        }

    }

    public void endAndSaveMatch(UUID matchUuid) {

        MatchScore matchScore = ongoingMatchesService.getMatchScore(matchUuid);


        if (!matchScore.isFinished()) {
            throw new InvalidInputException("Match is not finished yet.");
        }

        String winnerName = matchScore.getWinnerName()
                .orElseThrow(() -> new InvalidInputException("Winner name is empty."));

        Player firstPlayer = playerPersistenceService.getOrCreate(matchScore.getFirstPlayerName());
        Player secondPlayer = playerPersistenceService.getOrCreate(matchScore.getSecondPlayerName());

        Player winner = winnerName.equals(firstPlayer.getName()) ? firstPlayer : secondPlayer;

        Match finishewdMatch = new Match(firstPlayer, secondPlayer, winner);
        finishedMatchesPersistenceService.save(finishewdMatch);

        ongoingMatchesService.removeMatch(matchUuid);


    }


    public List<MatchResponseDto> getMatchDtos(int page, int size, String filter) {
        List<Match> matches = getMatches(page, size, filter);
        return MatchMapper.INSTANCE.toDtoList(matches);
    }



}
