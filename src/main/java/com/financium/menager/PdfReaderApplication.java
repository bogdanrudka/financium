package com.financium.menager;

import com.financium.menager.domain.User;
import com.financium.menager.repository.AccountRepository;
import com.financium.menager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class PdfReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfReaderApplication.class, args);
	}

	@Bean
	public CommandLineRunner fillDB(UserRepository repository, AccountRepository accountRepository){
		return (args -> {
			repository.save(new User("orudka", "Oksana", "Rudka"));
			repository.save(new User("brudka", "Bogdan", "Rudka"));
			repository.save(new User("mrudka", "Maria", "Rudka"));
			repository.save(new User("borudka", "Bogdan", "Rudka"));

//			log.info(accountRepository.save(new Checking(0.0)).getId().toString());
		});
	}
}
