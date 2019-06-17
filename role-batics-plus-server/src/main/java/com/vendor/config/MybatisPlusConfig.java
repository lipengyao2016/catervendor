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
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = "com.vendor.mapper" , sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisPlusConfig {

    @Resource(name = "roleDataSource")
    private DataSource dataSource;



    private Log log = LogFactory.getLog(MybatisPlusConfig.class);

    //mybatis plus 全局配置
    @Bean(name = "globalConfig")
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

        configuration.setSqlInjector(new LogicSqlInjector());
        return configuration;
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(
            @Qualifier(value = "globalConfig") GlobalConfig configuration) throws Exception{

        log.info("初始化SqlSessionFactory");
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean=new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Interceptor[] interceptor={new PaginationInterceptor(),new PerformanceInterceptor()
        ,new OptimisticLockerInterceptor()};
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
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(){
        log.info("初始化transactionManager");
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}