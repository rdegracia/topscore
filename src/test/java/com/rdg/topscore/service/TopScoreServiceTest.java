package com.rdg.topscore.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.rdg.topscore.dao.TopScoreDao;
import com.rdg.topscore.dto.RequestDto;
import com.rdg.topscore.dto.ResponseDto;
import com.rdg.topscore.dto.TopScoreRankDto;

public class TopScoreServiceTest {

    @InjectMocks
    private TopScoreService service;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws Exception{
        RequestDto dto = new RequestDto();
        dto.setPlayer("rose");
        dto.setScore(100);
        dto.setTime(new Date());
        TopScoreDao dao = Mockito.mock(TopScoreDao.class);
        doThrow(new RuntimeException()).when(dao).insertRequest(anyObject());
    }

    @Test
    public void testGetScore() throws Exception{
        Object[] obj = new Object[] {1, "rose", 100, new Date()};
        List<Object> object = new ArrayList<>();
        object.add(obj);

        TopScoreDao dao = Mockito.mock(TopScoreDao.class);
        TopScoreService service =  Mockito.mock(TopScoreService.class);

        doThrow(new RuntimeException()).when(dao).insertRequest(anyObject());
        when(dao.executeNativeSQLTemplate(anyString(), anyObject())).thenReturn(object);
        ResponseDto response = service.getScore("1");
        Assertions.assertNull(response);
    }
}
