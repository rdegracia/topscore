package com.rdg.topscore.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdg.topscore.dao.TopScoreDao;
import com.rdg.topscore.dto.PlayerHistoryDto;
import com.rdg.topscore.dto.RequestDto;
import com.rdg.topscore.dto.ResponseDto;
import com.rdg.topscore.dto.ResponseDtoByFilter;
import com.rdg.topscore.dto.TopScoreRankDto;


@Service
public class TopScoreService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Autowired
    private TopScoreDao dao;

    static final ObjectMapper mapper = new ObjectMapper();

    public ResponseDto create(RequestDto request) {
        logger.info("create START---------");
        ResponseDto response=new ResponseDto();
        if (request != null) {
            try {
                dao.insertRequest(request);
                response.setResultCode("N-000");
                response.setResultMessage("SUCCESS");
                TopScoreRankDto dto = new TopScoreRankDto();
                dto.setPlayer(request.getPlayer());
                dto.setScore(request.getScore());
                dto.setTime(request.getTime());
                response.setData(dto);
            } catch (Exception e) {
                response.setResultCode("E-000");
                response.setResultMessage("ERROR :" + e.getMessage());
            }

        }
        logger.info("create END---------");
        return response;
    }

    public ResponseDto getScore(String playerId) {
        logger.info("getScore START---------");
        ResponseDto response=new ResponseDto();
        if (playerId != null) {
            try {
                TopScoreRankDto dto = getTopRankInfo(Integer.parseInt(playerId));
                response.setResultCode("N-000");
                response.setResultMessage("SUCCESS");
                dto.setScore(dto.getScore());
                response.setData(dto);
            } catch (Exception e) {
                response.setResultCode("E-000");
                response.setResultMessage("ERROR :" + e.getMessage());
            }

        }
        logger.info("getScore END---------");
        return response;
    }

    public ResponseDto deleteScore(String playerId) {
        logger.info("deleteScore START---------");
        ResponseDto response=new ResponseDto();
        if (playerId != null) {
            try {
                //get info
                TopScoreRankDto dto = getTopRankInfo(Integer.parseInt(playerId));
                if (dto!=null) {
                    //delete
                    dao.executeNativeSQLTemplate("DELETE_SCORE", new Object[] {playerId});
                    response.setResultCode("N-000");
                    response.setResultMessage("SUCCESS");
                    response.setData(dto);
                }
            } catch (Exception e) {
                response.setResultCode("E-000");
                response.setResultMessage("ERROR :" + e.getMessage());
            }

        }
        logger.info("deleteScore END---------");
        return response;
    }

    public TopScoreRankDto getTopRankInfo(int playerId) throws Exception {
        logger.info("getTopRankInfo START---------");
        TopScoreRankDto dto=null;
        List<Object> topRank = dao.executeNativeSQLTemplate("GET_SCORE_BY_PLAYERID", new Object[] {playerId});
        if(topRank!=null && topRank.size()>0){
            for(int i=0; i<topRank.size(); i++) {
                Object[] retObj=(Object[]) topRank.get(i);
                if(retObj!=null){
                    Integer id=Integer.valueOf(retObj[0].toString());
                    String player=retObj[1].toString();
                    Integer score=Integer.valueOf(retObj[2].toString());
                    String dateInString = retObj[3].toString();
                    Date date = new SimpleDateFormat("dd/MMM/yyyy").parse(dateInString);

                    dto = new TopScoreRankDto();
                    dto.setPlayerId(id);
                    dto.setPlayer(player);
                    dto.setScore(score);
                    dto.setTime(date);

                    logger.info(dto.toString());
                }
            }
        }
        logger.info("getTopRankInfo END---------");
        return dto;
    }

    public ResponseDto getPlayersHistory(String player) {
        logger.info("getPlayersHistory END---------");
        ResponseDto response=null;
        try {
            if (player!=null) {
                response.setTop(getPlayersHistoryList("PLAYER_SCORE_TOP", player).get(0));
                response.setLow(getPlayersHistoryList("PLAYER_SCORE_LOW", player).get(0));
                response.setAvg(getPlayersHistoryList("PLAYER_SCORE_AVG", player).get(0));
                response.setHistList(getPlayersHistoryList("PLAYER_SCORE_ALL", player));
                response.setResultCode("N-000");
                response.setResultMessage("SUCCESS");
            }

        }catch(Exception e) {
            response.setResultCode("E-000");
            response.setResultMessage("ERROR :" + e.getMessage());
        }
        logger.info("getPlayersHistory END---------");
        return response;
    }

    public List<PlayerHistoryDto> getPlayersHistoryList(String template, String player) throws Exception {
        logger.info("getTopRankInfo START---------");
        List<PlayerHistoryDto> list= new ArrayList<PlayerHistoryDto>();
        if (player != null) {
            List<Object> topRank = dao.executeNativeSQLTemplate(template, new Object[] {player});
            if(topRank!=null && topRank.size()>0){
                for(int i=0; i<topRank.size(); i++) {
                    Object[] retObj=(Object[]) topRank.get(i);
                    if(retObj!=null){
                        Integer score=Integer.valueOf(retObj[0].toString());
                        String dateInString = retObj[2].toString();
                        Date date = new SimpleDateFormat("dd/MMM/yyyy").parse(dateInString);
                        PlayerHistoryDto dto = new PlayerHistoryDto();
                        dto.setScore(score);
                        dto.setTime(date);
                        logger.info(dto.toString());
                        list.add(dto);
                    }
                }
            }
        }
        logger.info("getTopRankInfo END---------");
        return list;
    }


    public ResponseDtoByFilter getAllScores() {
        logger.info("getAllScores START---------");
        ResponseDtoByFilter response = new ResponseDtoByFilter();
        List<TopScoreRankDto> allScores = new ArrayList<TopScoreRankDto>();
        try {
            List<Object> topRank = dao.executeNativeSQLTemplate("GET_ALL_SCORES", new Object[] {});
            if(topRank!=null && topRank.size()>0){
                for(int i=0; i<topRank.size(); i++) {
                    Object[] retObj=(Object[]) topRank.get(i);
                    if(retObj!=null){
                        Integer id=Integer.valueOf(retObj[0].toString());
                        String player=retObj[1].toString();
                        Integer score=Integer.valueOf(retObj[2].toString());
                        String dateInString = retObj[3].toString();
                        Date date = new SimpleDateFormat("dd/MMM/yyyy").parse(dateInString);

                        TopScoreRankDto dto = new TopScoreRankDto();
                        dto.setPlayerId(id);
                        dto.setPlayer(player);
                        dto.setScore(score);
                        dto.setTime(date);
                        allScores.add(dto);
                    }
                }
                response.setResultCode("N-000");
                response.setResultMessage("SUCCESS");
                Date dt = new SimpleDateFormat("dd/MMM/yyyy").parse("01/Nov/2020");
                response.setAllScoresAfterDate(getAllScoresFilterAfterDate(dt, allScores));
                Date before = new SimpleDateFormat("dd/MMM/yyyy").parse("01/Jan/2021");
                Date after = new SimpleDateFormat("dd/MMM/yyyy").parse("01/Jan/2020");
                response.setAllScoresBetweenDate(getAllScoresFilterBetweenDate(before, after, allScores));
                //response.setAllScoresByPlayer(getAllScoresFilterByPlayer(player, allScores));
                Date dt2 = new SimpleDateFormat("dd/MMM/yyyy").parse("01/Dec/2020");
                //response.setAllScoresByPlayersAndDate(getAllScoresFilterByPlayersAndDate(players, dt2, allScores));

            }
        } catch (Exception e) {
            response.setResultCode("E-000");
            response.setResultMessage("ERROR :" + e.getMessage());
        }
        logger.info("getAllScores END---------");
        return response;
    }

    private List<TopScoreRankDto> getAllScoresFilterByPlayer(String player, List<TopScoreRankDto> allScores) {
        return allScores
                .stream()
                .filter(dto -> dto.getPlayer().contains(player))
                .collect(Collectors.toList());
    }

    private List<TopScoreRankDto> getAllScoresFilterAfterDate(Date dt, List<TopScoreRankDto> allScores) {
        return allScores
                .stream()
                .filter(dto -> dto.getTime().after(dt))
                .collect(Collectors.toList());

    }

    private List<TopScoreRankDto> getAllScoresFilterByPlayersAndDate(String[] players, Date dt, List<TopScoreRankDto> allScores) {
        return allScores
                .stream()
                .filter(dto -> dto.getTime().before(dt) && Arrays.asList(players).contains(dto.getPlayer()))
                .collect(Collectors.toList());
    }

    private List<TopScoreRankDto> getAllScoresFilterBetweenDate(Date before, Date after, List<TopScoreRankDto> allScores) {
        return allScores
                .stream()
                .filter(dto -> dto.getTime().after(after) && dto.getTime().before(before))
                .collect(Collectors.toList());

    }

    private List<PlayerHistoryDto> getScoreByPlayer(String player) {
        List<PlayerHistoryDto> allScores = new ArrayList<PlayerHistoryDto>();
        try {
            if (player != null) {
                List<PlayerHistoryDto> playerScores = getPlayersHistoryList("GET_SCORE_BY_PLAYER", player);
                List<PlayerHistoryDto> playerScoresAfterPeriod = getPlayersHistoryList("GET_SCORE_AFTER_PERIOD", player);
                List<PlayerHistoryDto> playerScoresBetweenPeriod = getPlayersHistoryList("GET_SCORE_BETWEEN_PERIOD", player);

                allScores.addAll(playerScores);
                allScores.addAll(playerScoresAfterPeriod);
                allScores.addAll(playerScoresBetweenPeriod);

            }
        } catch(Exception e) {
            return null;
        }

        return allScores;

    }



}
