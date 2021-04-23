package org.ensas.jwtBrains.dao;

import org.ensas.jwtBrains.entities.AppUser;
import org.ensas.jwtBrains.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<AppUser, Long> {

	public AppUser findByUsername(String username);
}
