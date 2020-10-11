package com.bgokce.eventmanagement;

import com.bgokce.eventmanagement.usecases.manageperson.Authority;
import com.bgokce.eventmanagement.usecases.manageperson.AuthorityRepository;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
@EnableCaching
@EnableScheduling
public class EventManagementApplication {

	private final AuthorityRepository authorityRepository;
	private final PersonService personService;

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);

	}

	@PostConstruct
	private void initialize(){
		Authority authority = authorityRepository.findById(1L).orElse(null);
		if (authority == null){
			authorityRepository.save(new Authority("USER"));
			authorityRepository.save(new Authority("ADMIN"));
			personService.createAdmin("admin","admin");
		}
	}

}

