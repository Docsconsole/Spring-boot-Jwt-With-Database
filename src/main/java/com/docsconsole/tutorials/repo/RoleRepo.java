package com.docsconsole.tutorials.repo;

import com.docsconsole.tutorials.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}