package com.zensar.training.bean;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class JDBCConfigaration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds=new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.ci.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/TraininDb");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
		
	}
}
