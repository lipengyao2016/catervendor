package com.vendor.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.vendor.controller.RoleController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableApolloConfig
public class ApolloBootConfig {

    private Log log = LogFactory.getLog(ApolloBootConfig.class);

    //@Bean
    public ApolloConfigBean apolloConfigBean()
    {
        return new ApolloConfigBean();
    }

  /*  public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    private Config config;

    public Config getAnotherConfig() {
        return anotherConfig;
    }

    public void setAnotherConfig(Config anotherConfig) {
        this.anotherConfig = anotherConfig;
    }

    private Config anotherConfig;

    public String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }

    @Value("${server.domain}")
    private String serverDomain;

    public  ApolloBootConfig()
    {
        config = ConfigService.getAppConfig();

        log.info(" RoleServer.host:  " + config.getProperty("RoleServer.host",""));

        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                log.info("Changes for namespace {}" +changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    log.info("Change -application config key: ," +change.getPropertyName() +
                                    " oldValue: , " +change.getOldValue() +
                                            "newValue: , " +change.getNewValue() +
                                                    "changeType:" + change.getChangeType());


                }
            }
        });

        this.anotherConfig = ConfigService.getConfig("TEST1.Common");

        log.info(" knex.connection.host:  " + anotherConfig.getProperty("knex.connection.host",""));

        anotherConfig.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                log.info("Changes for namespace {}" +changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    log.info("Change -common config key: ," +change.getPropertyName() +
                            " oldValue: , " +change.getOldValue() +
                            "newValue: , " +change.getNewValue() +
                            "changeType:" + change.getChangeType());
                }
            }
        });
    }*/
}
