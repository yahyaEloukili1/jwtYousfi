package org.ensas.jwtBrains.service;

import org.ensas.jwtBrains.entities.AppRole;
import org.ensas.jwtBrains.entities.AppUser;

public interface AccountService {

	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username,String roleName);
	public AppUser findUserByUsername(String username);
}
