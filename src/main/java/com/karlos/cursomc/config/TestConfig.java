package com.karlos.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.karlos.cursomc.services.DBService;
import com.karlos.cursomc.services.EmailService;
import com.karlos.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() { // o Autowired do spring vai instanciar este BEAN automaticamente
		return new MockEmailService();
	}

}
