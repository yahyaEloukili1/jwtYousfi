package org.ensas.jwtBrains.web;

import javax.management.RuntimeErrorException;

import org.ensas.jwtBrains.entities.AppUser;
import org.ensas.jwtBrains.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {

	@Autowired
	private AccountService accountService;
	@PostMapping("/register")
	public AppUser register(@RequestBody AppUser appUser) {
		if(!appUser.getPassword().equals(appUser.getRepaswword())) {
			throw new RuntimeException("You must confirm your password");
		}
		AppUser user = accountService.findUserByUsername(appUser.getUsername());
		if(user!=null) {
			throw new RuntimeException("This user already exist");
		}
	
		
		 accountService.saveUser(appUser);
		 accountService.addRoleToUser(appUser.getUsername(), "USER");
		 return appUser;
		 
	}
}
