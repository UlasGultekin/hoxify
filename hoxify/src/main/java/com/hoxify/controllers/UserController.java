package com.hoxify.controllers;

import com.hoxify.entities.User;
import com.hoxify.error.ApiError;
import com.hoxify.repositories.UserRepository;
import com.hoxify.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    private static final Logger log= LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @PostMapping("api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user){
        String userName=user.getUsername();
        if (userName==null || userName.isEmpty()){
            ApiError apiError= new ApiError(400,"Validation Error","/api/1.0/users");
            Map<String,String> validationErrors=new HashMap<>();
            validationErrors.put("username","Username cannot be null");
            apiError.setValidationErrors(validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }
        userService.save(user);
        log.info(user.toString());
        return ResponseEntity.ok(new String("User created"));
    }
}
