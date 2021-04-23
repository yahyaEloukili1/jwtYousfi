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
	public AppUser register(@RequestBody RegisterForm userForm) {
		if(!userForm.getPassword().equals(userForm.getRepaswword())) {
			throw new RuntimeException("You must confirm your password");
		}
		AppUser user = accountService.findUserByUsername(userForm.getUsername());
		if(user!=null) {
			throw new RuntimeException("This user already exist");
		}
		AppUser appUser = new AppUser();
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		
		 accountService.saveUser(appUser);
		 accountService.addRoleToUser(userForm.getUsername(), "USER");
		 return appUser;
		 
	}
}
