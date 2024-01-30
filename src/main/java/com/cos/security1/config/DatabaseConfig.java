package com.cos.security1.config;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
	
	@Value("${driver-class-name}")
	private String driver;
	@Value("${oracle-url}")
	private String url;
	@Value("${user}")
	private String user;
	@Value("${password}")
	private String password;
	
	@Bean
	DataSource dataSource() {
		// 이거는 나중에 지우자
		System.out.println(driver);
		System.out.println(url);
		System.out.println(user);
		System.out.println(password);
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driver);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(user);
		hikariConfig.setPassword(password);
		hikariConfig.setAutoCommit(true);
		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setMaximumPoolSize(10);
		return new HikariDataSource(hikariConfig);
	}/////
	@Bean
	DataSourceTransactionManager transactionManager(HikariDataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}	
	@Bean
	TransactionTemplate transactionTemplate(DataSourceTransactionManager transactionManager) {
		TransactionTemplate transactionTemplate= new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager);
		return transactionTemplate;
	}
}
