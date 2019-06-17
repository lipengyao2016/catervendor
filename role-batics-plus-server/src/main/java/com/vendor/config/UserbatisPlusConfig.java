package com.vendor.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = "com.vendor.user_mapper",sqlSessionFactoryRef = "user_sqlSessionFactory")
public class UserbatisPlusConfig {

    @Resource(name = "userDataSource")
    private DataSource dataSource;



    private Log log = LogFactory.getLog(UserbatisPlusConfig.class);

    //mybatis plus 全局配置
    @Bean(name = "user_globalConfig")
    public GlobalConfig globalConfiguration(){
        log.info("初始化GlobalConfiguration");
        GlobalConfig configuration=new GlobalConfig();
/*        //主键策略
        configuration.setRefresh(refreshMapper);
        configuration.setIdType(idType);
        //字段策略
        configuration.setFieldStrategy(fieldStrategy);
        //数据库大写 下划线转换
        configuration.setCapitalMode(capitalMode);*/
       // configuration.setSqlInjector(new LogicSqlInjector());
        return configuration;
    }


    @Bean(name = "user_sqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(
            @Qualifier(value = "user_globalConfig") GlobalConfig configuration) throws Exception{

        log.info("初始化SqlSessionFactory");
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean=new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Interceptor[] interceptor={new PaginationInterceptor(),new PerformanceInterceptor()
                /*,new OptimisticLockerInterceptor()*/};
        sqlSessionFactoryBean.setPlugins(interceptor);
        //ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        try{
            sqlSessionFactoryBean.setGlobalConfig(configuration);
            //sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
           // sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
            return sqlSessionFactoryBean.getObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sqlSessionFactoryBean.getObject();

    }
    @Bean(name = "user_transactionManager")
    public DataSourceTransactionManager transactionManager(){
        log.info("初始化transactionManager");
        return new DataSourceTransactionManager(dataSource);
    }

  /*  @Bean(name = "user_sqlSessionTemplate")
    public SqlSessionTemplate createUserSqlSessionTemplate(@Qualifier("user_sqlSessionFactory")
            SqlSessionFactory sqlSessionFactory)
    {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }*/



}