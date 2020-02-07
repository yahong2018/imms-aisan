package com.zhxh.imms.data;

import com.zhxh.imms.utils.Logger;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zhxh.imms.**.mapper.**", sqlSessionTemplateRef = "immsSqlSessionTemplate")
public class DataSourceConfig {
    @Bean(name = "immsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.zhxh-imms")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "immsSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("immsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/com/zhxh/imms/**/mapper/*.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    @Bean(name = "immsTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("immsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "immsSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("immsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "immsTransactionDefinition")
    public TransactionDefinition transactionDefinition(@Qualifier("immsTransactionManager") PlatformTransactionManager transactionManager){
       return new TransactionTemplate(transactionManager);
    }
}
