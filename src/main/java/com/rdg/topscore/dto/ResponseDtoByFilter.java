package com.rdg.topscore.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class ResponseDtoByFilter {

    private String resultCode;
    private String resultMessage;
    private List<TopScoreRankDto> allScoresByPlayer;
    private List<TopScoreRankDto> allScoresAfterDate;
    private List<TopScoreRankDto> allScoresByPlayersAndDate;
    private List<TopScoreRankDto> allScoresBetweenDate;
}
