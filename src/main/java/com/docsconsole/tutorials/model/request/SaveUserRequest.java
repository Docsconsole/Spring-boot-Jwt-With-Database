package com.docsconsole.tutorials.model.request;

import com.docsconsole.tutorials.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {
    
    private String username;
    private String password;
    private String email;
    private String name;
    private List<String> allowedRoles;

    public User getUserFromRequest(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAllowedRoles(allowedRoles);
        return user;
    }
    
}