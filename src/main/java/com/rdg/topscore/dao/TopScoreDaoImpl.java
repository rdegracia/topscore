package com.rdg.topscore.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rdg.topscore.dto.RequestDto;

@Repository(value = "topScoreDAO")
@Transactional(value="TransactionManager", readOnly=false)
public class TopScoreDaoImpl implements TopScoreDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NativeSQLManager nativeSQLManager;

    @SuppressWarnings("unchecked")
    public List<Object>  executeNativeSQLTemplate(String templateName, Object[] params) throws Exception {
        try {
            return nativeSQLManager.executeNativeSQLTemplate(templateName, params);

        } catch(HibernateException e) {
            throw new Exception(e);

        }

    }

    @SuppressWarnings("unchecked")
    public void insertRequest (RequestDto request) throws Exception {
        logger.info("insertRequest START -------------");
        try {
            nativeSQLManager.insertWithQuery(request);

        } catch(HibernateException e) {
            throw new Exception(e);
        }
        logger.info("insertRequest END -------------");
    }

}
