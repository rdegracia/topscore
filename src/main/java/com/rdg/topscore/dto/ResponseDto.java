package com.rdg.topscore.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class ResponseDto {

    private String resultCode;
    private String resultMessage;
    private TopScoreRankDto data;
    private PlayerHistoryDto top;
    private PlayerHistoryDto low;
    private PlayerHistoryDto avg;
    private List<PlayerHistoryDto> histList;
    private List<TopScoreRankDto> allScores;

}
