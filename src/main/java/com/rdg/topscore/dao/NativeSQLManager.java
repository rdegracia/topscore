package com.rdg.topscore.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Repository;

import com.rdg.topscore.config.DataSourceConfig;
import com.rdg.topscore.dto.RequestDto;

@Repository(value = "nativeSQLManager")
@ConditionalOnClass(DataSourceConfig.class)
public class NativeSQLManager {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    Logger logger = LoggerFactory.getLogger(NativeSQLManager.class);

    @SuppressWarnings("rawtypes")
    public List executeNativeSQLTemplate(String templateName, Object[] params) throws Exception {
        Query query = getSessionFactory().getCurrentSession().getNamedQuery(templateName);
        StringBuffer logBuffer = new StringBuffer();
        logger.info("executeNativeSQLTemplate start::::::::::::templateName:"+templateName);
        List result = null;
        try {
            if (params != null) {
                int loopSize = params.length;
                for (int i = 0; i < loopSize; i++) {
                    query.setParameter(i, params[i] != null ? params[i].toString() : "");
                }
            }
            result = query.list();
            logger.info(logBuffer.toString());
        }
        catch (Exception e) {
            logger.info(logBuffer.toString());
            logger.error(e.getMessage(), e);
            System.out.println(e);
            e.printStackTrace();
        }

        logger.info("executeNativeSQLTemplate end::::::::::::templateName:"+templateName);

        return result != null ? result : new ArrayList(0);
    }

    public void insertWithQuery(RequestDto request) {
        logger.info("insertWithQuery start::::::::::::");
        getSessionFactory().getCurrentSession().createNativeQuery("INSERT INTO top_score_rank (player, score, time) VALUES (?,?,?)")
          .setParameter(1, request.getPlayer())
          .setParameter(2, request.getScore())
          .setParameter(3, request.getTime())
          .executeUpdate();
        logger.info("insertWithQuery end::::::::::::");
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
