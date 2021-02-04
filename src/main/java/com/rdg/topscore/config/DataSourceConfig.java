/**
 *
 */
package com.rdg.topscore.config;

import java.util.Properties;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ConditionalOnProperty(name = "hikari.datasource.rdg.user")
public class DataSourceConfig {

	private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.connection.release_mode:auto}")
    private String hibernateConReleaseMode;

    @Value("${hibernate.show_sql:false}")
    private boolean hibernateShowSQL;

    @Value("${hibernate.generate_statistics:false}")
    private boolean hibernateGenStats;

    @Value("${hibernate.statement_cache.size:-1}")
    private int hibernateStatementCacheSize;

    @Value("${hibernate.jdbc.batch_size:-1}")
    private int hibernateJDBCBatchSize;

    @Value("${hibernate.cache.use_second_level_cache:false}")
    private boolean hibernate2LCache;

    @Value("${hibernate.cache.use_query_cache:false}")
    private boolean hibernateQryCache;

    @Value("${hibernate.cache.use_minimal_puts:false}")
    private boolean hibernateMinPutCache;

    @Value("${hibernate.cache.region.factory_class:NA}")
    private String hibernateRegionFactoryClass;

    @Value("${hibernate.cache.hazelcast.use_native_client:false}")
    private boolean hibernateUseNativeClientCache;

	@Value("${rdgs.connection.pool.provider:HikariCP}")
	private String connectionPoolProvider;

	@Value("${hikari.datasource.rdg.user}")
	private String hikariCPUserName;

	@Value("${hikari.datasource.rdg.password}")
	private String hikariCPPassword;

	@Value("${hikari.datasource.rdg.jdbcUrl}")
	private String hikariCPJDBCUrl;

	@Value("${hikari.datasource.rdg.dataSourceClassName:NA}")
	private String hikariCPDataSource;

	@Value("${hikari.datasource.rdg.poolName:NA}")
	private String hikariCPPoolName;

	@Value("${hikari.datasource.rdg.maxPoolSize:-1}")
	private int hikariCPMaxPoolSize;

	@Value("${hikari.datasource.rdg.minIdle:-1}")
	private int hikariCPMinIdle;

	@Value("${hikari.datasource.rdg.idleTimeOut:-1}")
	private long hikariCPIdleTimeout;

	@Value("${hikari.datasource.rdg.connectionTimeout:-1}")
	private long hikariCPConnectionTimeout;

	@Value("${hikari.datasource.rdg.leakDetectionThreshold:-1}")
	private long hikariCPLeakDetection;

	@Value("${hikari.datasource.rdg.autoCommitOnClose:false}")
	private boolean autoCommitOnClose;

	@Value("${hikari.datasource.rdg.maxLifetime:-1}")
	private long hikariCPMaxLifeTime;

	@Value("${hikari.datasource.rdg.driverClass:NA}")
	private String driverClass;

	@Value("${rdg.connection.entitymanager.mappingResources:NA}")
	private String mappingResource;


	static HikariDataSource ds = new HikariDataSource();

	@ConditionalOnProperty(name = "rdg.connection.pool.provider", havingValue="HikariCP")
	public HikariDataSource hikariDSRdg() throws Exception {
		ds.setAutoCommit(false);
		ds.addDataSourceProperty("user", hikariCPUserName);
		ds.addDataSourceProperty("password", hikariCPPassword);
		ds.addDataSourceProperty("url", hikariCPJDBCUrl);


		logger.info("Using {} DB connection string:: {}/*******@{}", hikariCPUserName, hikariCPJDBCUrl);
		ds.setDataSourceClassName(hikariCPDataSource);
		ds.setPoolName(hikariCPPoolName);

		if (hikariCPMinIdle>=0){
			ds.setMinimumIdle(hikariCPMinIdle);
		}

		if (hikariCPIdleTimeout>=0){
			ds.setIdleTimeout(hikariCPIdleTimeout);
		}

		if (hikariCPMaxPoolSize>=0){
			ds.setMaximumPoolSize(hikariCPMaxPoolSize);
		}

		if (hikariCPConnectionTimeout>=0){
			ds.setConnectionTimeout(hikariCPConnectionTimeout);
		}

		if (hikariCPLeakDetection>0){
			ds.setLeakDetectionThreshold(hikariCPLeakDetection);
		}

		if (hikariCPMaxLifeTime>0){
			ds.setMaxLifetime(hikariCPMaxLifeTime);
		}

		return ds;
	}


	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean sessionFactoryRdg() throws Exception {
		logger.info("Starting Database Auto Configuration for {} Schema. Using {} as Connection Pool provider... ", connectionPoolProvider);
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(hikariDSRdg());

		sessionFactoryBean.setMappingResources(mappingResource.split(","));

		sessionFactoryBean.setHibernateProperties(hibernatePropertiesRdg());

		return sessionFactoryBean;
	}

    private Properties hibernatePropertiesRdg() throws Exception{
        logger.info("Setting up {} DataSource hibernate properties...");
        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.dialect", hibernateDialect);
        hibernateProperties.put("hibernate.connection.release_mode", hibernateConReleaseMode);
        hibernateProperties.put("hibernate.show_sql", hibernateShowSQL);
        hibernateProperties.put("hibernate.generate_statistics", hibernateGenStats);
        hibernateProperties.put("hibernate.cache.use_second_level_cache", hibernate2LCache);
        hibernateProperties.put("hibernate.cache.use_query_cache", hibernateQryCache);

        return hibernateProperties;
    }

    @Bean(name="TransactionManager")
    public HibernateTransactionManager transactionManagerCustom() throws Exception {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryRdg().getObject());
        return transactionManager;
    }

	@PreDestroy
	public void shutDownHikariCPDS() {
		ds.close();
	}

}