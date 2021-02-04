package com.rdg.topscore.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.rdg.topscore.dto.RequestDto;
import com.rdg.topscore.dto.TopScoreRankDto;

public class TopScoreDaoImplTest {

   @Autowired
   private TopScoreDaoImpl dao;

   @Test
   @Transactional
   public void insertTest() {
       try {
           RequestDto dto = new RequestDto();
           dto.setPlayer("rose");
           dto.setScore(100);
           dto.setTime(new Date());
           dao.insertRequest(dto);
           List<Object> topRank = dao.executeNativeSQLTemplate("GET_SCORE_BY_PLAYER", new Object[] {"rose"});
           List<TopScoreRankDto> actual= getActualValue(topRank);
           Assertions.assertEquals(dto.getPlayer(), actual.get(0).getPlayer());
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


   private List<TopScoreRankDto> getActualValue(List<Object> topRank) throws Exception {
       List<TopScoreRankDto> list=new ArrayList<TopScoreRankDto>();
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
                   list.add(dto);
               }
           }
       }
       return list;
   }
}
