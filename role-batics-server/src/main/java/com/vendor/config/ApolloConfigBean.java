package com.vendor.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class ApolloConfigBean {
    private Log log = LogFactory.getLog(ApolloConfigBean.class);

    private static final Logger logger = LoggerFactory.getLogger(ApolloConfigBean.class);

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Config getAnotherConfig() {
        return anotherConfig;
    }

    public void setAnotherConfig(Config anotherConfig) {
        this.anotherConfig = anotherConfig;
    }

    @ApolloConfig
    private Config config;

    @ApolloConfig("TEST1.Common")
    private Config anotherConfig;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Value("${knex.connection.database:tt}")
    private String databaseName;

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    @Value("${redis.host:zz}")
    private String redisHost;

    @PostConstruct
    void initialize() {

        log.info("Keys for config: {}" + config.getPropertyNames().toString());
        log.info("Keys for anotherConfig: {}"+ anotherConfig.getPropertyNames().toString());
    }


    @ApolloConfigChangeListener({ "application", "TEST1.Common" })
    private void anotherChangeHandler(ConfigChangeEvent changeEvent) {
        logger.info("[anotherChangeHandler]Changes for namespace {}", changeEvent.getNamespace());
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            logger.info("[anotherChangeHandler]Change - key: {}, oldValue: {}, newValue: {}, changeType: {}", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());
        }
    }

    public String getProperty(String key)
    {
        String  value = config.getProperty(key,"");
        if(StringUtils.isNotEmpty(value))
        {
            return value;
        }
        else {
            return anotherConfig.getProperty(key,"");
        }
    }


}
