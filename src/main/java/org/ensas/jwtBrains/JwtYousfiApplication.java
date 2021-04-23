package org.ensas.jwtBrains;

import java.util.stream.Stream;

import org.ensas.jwtBrains.dao.TaskRepository;
import org.ensas.jwtBrains.entities.AppRole;
import org.ensas.jwtBrains.entities.AppUser;
import org.ensas.jwtBrains.entities.Task;
import org.ensas.jwtBrains.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtYousfiApplication implements CommandLineRunner {

	@Autowired
	TaskRepository taskRepository;
	@Autowired
	AccountService accountService;
	public static void main(String[] args) {
		SpringApplication.run(JwtYousfiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bcrypt() {
		return new BCryptPasswordEncoder();
	}
	@Override
	public void run(String... args) throws Exception {
		
		Stream.of("T1","T2","T3").forEach(t->{
			taskRepository.save(new Task(null,t));
		});
		accountService.saveUser(new AppUser(null,"admin","1234",null));
		accountService.saveUser(new AppUser(null,"user","1234",null));
		accountService.saveRole(new AppRole(null,"ADMIN"));
		accountService.saveRole(new AppRole(null,"USER"));
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("user", "USER");
	}

}
