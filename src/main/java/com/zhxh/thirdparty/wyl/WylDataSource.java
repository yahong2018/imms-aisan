package com.zhxh.thirdParty.wyl;

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

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zhxh.thirdParty.wyl.**.mapper.**",sqlSessionTemplateRef = "thirdParty_wyl_SqlSessionTemplate")
public class WylDataSource {
    @Bean(name = "thirdParty_wyl_DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.zhxh-third-party-wyl")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "thirdParty_wyl_SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("thirdParty_wyl_DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/com/zhxh/thirdParty/wyl/**/mapper/*.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    @Bean(name = "thirdParty_wyl_TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("thirdParty_wyl_DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "thirdParty_wyl_SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("thirdParty_wyl_SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
