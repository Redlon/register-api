package com.api.register.controller;

import com.api.register.model.User;
import com.api.register.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String home(Principal principal){
        return "Hello, " + principal.getName();
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        try {
            List<User> userList = new ArrayList<>(userRepository.findAll());
            if (userList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{uuid}")
    public ResponseEntity<User> getUserById(@PathVariable UUID uuid){
        Optional<User> optionalUser = userRepository.findById(uuid);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser = new User();
        LocalDateTime now = LocalDateTime.now();
        newUser.setCreated(now);
        newUser.setModified(now);
        newUser.setLastLogin(now);
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/updateUserById/{uuid}")
    public ResponseEntity<User> updateUserById(@PathVariable UUID uuid,
                                               @RequestBody User user){
        Optional<User> oldUser = userRepository.findById(uuid);

        if (oldUser.isPresent()){
            User modifiedUser = oldUser.get();
            modifiedUser.setModified(LocalDateTime.now());

            userRepository.save(modifiedUser);
            return new ResponseEntity<>(modifiedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUserById/{uuid}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable UUID uuid){
        userRepository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
