package com.docsconsole.tutorials.controller;

import com.docsconsole.tutorials.model.entity.User;
import com.docsconsole.tutorials.model.request.CreateTokenRequest;
import com.docsconsole.tutorials.model.request.SaveUserRequest;
import com.docsconsole.tutorials.model.request.ValidateTokenRequest;
import com.docsconsole.tutorials.model.response.ValidateTokenResponse;
import com.docsconsole.tutorials.service.security.JWTAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
public class JWTAuthController {


    @Autowired
    private JWTAuthService jWTAuthService;

    @RequestMapping(value = "/create/token", method = RequestMethod.POST)
    public ResponseEntity<?> createToken(@RequestBody CreateTokenRequest request) throws Exception {
        return jWTAuthService.createToken(request);
    }

    @RequestMapping(value="/user/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody SaveUserRequest user){
        return jWTAuthService.saveUser(user);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/validate/userToken", produces = "application/json")
    public ResponseEntity<?> validateUserToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
        return jWTAuthService.validateUserToken();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/validate/adminToken", produces = "application/json")
    public ResponseEntity<?> validateAdminToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
        return ResponseEntity.ok(new ValidateTokenResponse("succeeded"));
    }

}
