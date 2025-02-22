package com.cpt.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(value="com.cpt.service")
public class DBConfig {
	
	 private final ApplicationContext applicationContext;

	    @Autowired
	    DBConfig(ApplicationContext applicationContext){
	        this.applicationContext = applicationContext;
	    }

	    @Bean
	    @ConfigurationProperties(prefix="spring.datasource.hikari")
	    public HikariConfig hikariConfig() {
	        return new HikariConfig();
	    }

	    @Bean
	    public DataSource dataSource() throws Exception {
	        DataSource dataSource = new HikariDataSource(hikariConfig());
	        return dataSource;
	    }

	    @Bean
	    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	        sqlSessionFactoryBean.setDataSource(dataSource);
	        sqlSessionFactoryBean.setTypeAliasesPackage("com.cpt");
	        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/*.xml"));
	        return sqlSessionFactoryBean.getObject();
	    }

	    @Bean
	    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

}
