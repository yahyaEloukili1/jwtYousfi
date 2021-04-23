package org.ensas.jwtBrains.service;

import java.util.ArrayList;
import java.util.Collection;

import org.ensas.jwtBrains.entities.AppRole;
import org.ensas.jwtBrains.entities.AppUser;
import org.ensas.jwtBrains.entities.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService{

	@Autowired
	private AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = accountService.findUserByUsername(username);
		if(user == null) {
		
			throw new UsernameNotFoundException("not found");
		}
		return new MyUserDetails(user);
	}

}
