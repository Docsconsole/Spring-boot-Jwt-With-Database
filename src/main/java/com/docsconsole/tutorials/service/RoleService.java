package com.docsconsole.tutorials.service;

import com.docsconsole.tutorials.repo.RoleRepo;
import com.docsconsole.tutorials.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public Role findByName(String name) {
        Role role = roleRepo.findRoleByName(name);
        return role;
    }
}
