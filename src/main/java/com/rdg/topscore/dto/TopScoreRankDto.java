package com.rdg.topscore.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class TopScoreRankDto {

    private Integer playerId;
    private String player;
    private Integer score;
    private Date time;

}
