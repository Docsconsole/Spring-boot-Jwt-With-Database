package com.docsconsole.tutorials.service;

import com.docsconsole.tutorials.model.entity.Role;
import com.docsconsole.tutorials.model.entity.User;
import com.docsconsole.tutorials.model.request.SaveUserRequest;
import com.docsconsole.tutorials.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepo userRepo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public User findOne(String username) {
        return userRepo.findByUsername(username);
    }

    public User save(SaveUserRequest requestedUser) {

        User user = requestedUser.getUserFromRequest();
        user.setPassword( new BCryptPasswordEncoder().encode(requestedUser.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        for (String allowedRole : user.getAllowedRoles()) {
            if("USER".equalsIgnoreCase(allowedRole)){
                Role role = roleService.findByName("USER");
                roleSet.add(role);
            }
            if("ADMIN".equalsIgnoreCase(allowedRole)){
                Role role = roleService.findByName("ADMIN");
                roleSet.add(role);
            }
        }

        user.setRoles(roleSet);
        return userRepo.save(user);
    }
}
