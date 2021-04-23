package org.ensas.jwtBrains.dao;

import org.ensas.jwtBrains.entities.AppRole;
import org.ensas.jwtBrains.entities.AppUser;
import org.ensas.jwtBrains.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<AppRole, Long> {

	public AppRole findByRoleName(String rolename);
}
