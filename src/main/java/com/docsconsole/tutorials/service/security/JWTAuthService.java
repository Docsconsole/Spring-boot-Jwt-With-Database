package com.docsconsole.tutorials.service.security;


import com.docsconsole.tutorials.model.entity.User;
import com.docsconsole.tutorials.model.request.CreateTokenRequest;
import com.docsconsole.tutorials.model.request.SaveUserRequest;
import com.docsconsole.tutorials.model.response.CreateTokenResponse;
import com.docsconsole.tutorials.model.response.ValidateTokenResponse;
import com.docsconsole.tutorials.service.UserService;
import com.docsconsole.tutorials.util.JWTTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenUtils jwtTokenUtils;
    @Autowired
    private UserService userService;

    public ResponseEntity<?>  createToken(CreateTokenRequest request) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( request.getUsername(), request.getPassword() )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtils.generateToken(authentication);
        return ResponseEntity.ok(new CreateTokenResponse(token));
    }

    public User saveUser(SaveUserRequest user){
        return userService.save(user);
    }

    public ResponseEntity<?> validateUserToken(){
        return ResponseEntity.ok(new ValidateTokenResponse("succeeded"));
    }

    public ResponseEntity<?> validateAdminToken(){
        return ResponseEntity.ok(new ValidateTokenResponse("succeeded"));
    }
}
