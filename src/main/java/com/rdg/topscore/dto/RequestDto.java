package com.rdg.topscore.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    @NotNull
    private String player;

    @NotNull
    private Integer score;

    @NotNull
    private Date time;

}
