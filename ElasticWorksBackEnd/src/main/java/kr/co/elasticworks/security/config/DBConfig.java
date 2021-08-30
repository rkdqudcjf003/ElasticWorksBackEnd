package kr.co.elasticworks.security.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import kr.co.elasticworks.ElasticWorksBackEndApplication;

@Configuration
@PropertySource("classpath:/application.yml")
public class DBConfig {
	private static final Logger log = LoggerFactory.getLogger(ElasticWorksBackEndApplication.class);

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		log.info("datasource : {}너는 누구냐", dataSource);
		return dataSource;
	}
}
