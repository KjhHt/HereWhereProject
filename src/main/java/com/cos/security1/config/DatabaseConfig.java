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
	
	
	//데이타 베이스 연결정보 주입
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
		//1.HikariConfig객체 생성후 데이타베이스 연결 및 커넥션 풀 정보 설정
		//JDBC연결 정보 설정
		System.out.println(driver);
		System.out.println(url);
		System.out.println(user);
		System.out.println(password);
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driver);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(user);
		hikariConfig.setPassword(password);
		//히카리 커넥션 풀 관련 설정 추가
		//자동 커밋 설정(기본값 true)
		hikariConfig.setAutoCommit(true);
		//IDLE상태에 있는 커넥션이 없을 때 즉 모든 커넥션이 사용 중일때
		//ConnectionTimeout이 지날 때까지 getConnection() 호출은 블록킹된다(기본값:30초)
		hikariConfig.setConnectionTimeout(30000);
		//커넥션 풀에 최대 커넥션 수.(기본값: 10)
		hikariConfig.setMaximumPoolSize(10);
		//DataSource를 상속받은 HikariDataSource객체 반환(싱글톤)
		return new HikariDataSource(hikariConfig);
	}/////
	//트랜잭션 처리를 위한 빈 등록
	//트랜잭션 관리자 객체
	@Bean
	DataSourceTransactionManager transactionManager(HikariDataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}	
	//트랜잭션 관리자 객체를 TransactionTemplate의 트랜잭션 관리자로 설정
	//TransactionTemplate으로 서비스단에서 트랜잭션 처리한다
	@Bean
	TransactionTemplate transactionTemplate(DataSourceTransactionManager transactionManager) {
		TransactionTemplate transactionTemplate= new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager);
		return transactionTemplate;
	}
}
