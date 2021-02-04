package com.rdg.topscore.dao;

import java.util.List;

import com.rdg.topscore.dto.RequestDto;

public interface TopScoreDao {

    public List<Object>  executeNativeSQLTemplate(String templateName, Object[] params) throws Exception;
    public void insertRequest (RequestDto request) throws Exception;

}
