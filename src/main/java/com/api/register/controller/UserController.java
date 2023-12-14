package com.api.register.controller;

import com.api.register.model.User;
import com.api.register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getUserById/{uuid}")
    public ResponseEntity<User> getUserById(@PathVariable UUID uuid){
        return userService.getUserById(uuid);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, Object>> addUser(@RequestHeader("Authorization") String bearerToken,
                                                       @RequestBody Map<String, Object> user){
        return userService.addUser(bearerToken, user);
    }

    @PutMapping("/updateUserById/{uuid}")
    public ResponseEntity<User> updateUserById(@PathVariable UUID uuid,
                                               @RequestBody User user){
        return userService.updateUserById(uuid, user);
    }

    @DeleteMapping("/deleteUserById/{uuid}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable UUID uuid){
        return userService.deleteUserById(uuid);
    }
}
