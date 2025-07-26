package com.example.tennisscoreboard.mapper;

import com.example.tennisscoreboard.dto.MatchResponseDto;
import com.example.tennisscoreboard.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(source = "player1.name", target = "playerFirstName")
    @Mapping(source = "player2.name", target = "playerSecondName")
    @Mapping(source = "winner.name", target = "playerWinner")
    MatchResponseDto toDto(Match match);

    List<MatchResponseDto> toDtoList(List<Match> matches);
}
