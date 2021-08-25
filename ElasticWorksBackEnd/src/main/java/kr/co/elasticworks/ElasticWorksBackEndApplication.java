package kr.co.elasticworks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kr.co.elasticworks.api.mapper.UserMapper;

@SpringBootApplication
public class ElasticWorksBackEndApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(ElasticWorksBackEndApplication.class, args);
	}


}
