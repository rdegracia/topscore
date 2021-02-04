package com.rdg.topscore.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rdg.topscore.dto.RequestDto;
import com.rdg.topscore.dto.ResponseDto;
import com.rdg.topscore.dto.ResponseDtoByFilter;
import com.rdg.topscore.service.TopScoreService;

@RestController
@RequestMapping(value = "/topscore", produces = MediaType.APPLICATION_JSON_VALUE)
public class TopScoreController {

    private static final Logger LOG = LoggerFactory.getLogger(TopScoreController.class);

    @Autowired
    private TopScoreService service;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto createScore(@Valid @NotNull @RequestBody final RequestDto request){
        LOG.info("/create");
        return service.create(request);
    }

    @PostMapping(value = "/getScore")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto getScore(@PathVariable String playerId) {
        LOG.info("/getScore");
        return service.getScore(playerId);
    }

    @PostMapping(value = "/deleteScore")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto deleteScore(@PathVariable String playerId){
        LOG.info("/delete");
        return service.deleteScore(playerId);
    }

    @PostMapping(value = "/getPlayersHistory")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto getPlayersHistory(@PathVariable String player) {
        LOG.info("/getScore");
        return service.getScore(player);
    }

    @PostMapping(value = "/getAllScores")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDtoByFilter getAllScores() {
        LOG.info("/getAllScores");
        return service.getAllScores();
    }

}
