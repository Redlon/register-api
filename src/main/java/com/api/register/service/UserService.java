package com.api.register.service;

import com.api.register.model.User;
import com.api.register.model.dto.OutputUserDTO;
import com.api.register.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneService phoneService;

    public ResponseEntity<Map<String, Object>> addUser(String bearerToken, Map<String, Object> user) {
        Map<String, Object> response = new HashMap<>();
        //Check if mail is valid
        if (!validation((String) user.get("email"), true)){
            response.put("mensaje", "El mail ingresado no es valido");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //CHeck if email exist
        if (userRepository.existsByEmail((String) user.get("email"))){
            response.put("mensaje", "El mail ingresado ya existe");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!validation((String) user.get("password"), false)){
            response.put("mensaje", "La contrasena ingresada no es valida");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User newUser = createAndSaveUser(bearerToken, user);

        OutputUserDTO userDTO = OutputUserDTO.outputUserDTO(newUser);

        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Map<String, Object> map = objectMapper.convertValue(userDTO, new TypeReference<>() {});

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private User createAndSaveUser(String bearerToken, Map<String, Object> user) {
        User newUser = new User();
        newUser.setName((String) user.get("name"));
        newUser.setEmail((String) user.get("email"));
        newUser.setPassword((String) user.get("password"));
        LocalDateTime now = LocalDateTime.now();
        newUser.setCreated(now);
        newUser.setModified(now);
        newUser.setLastLogin(now);
        newUser.setToken(bearerToken);
        userRepository.save(newUser);
        phoneService.createAndSavePhoneList((List<Map<String, Object>>) user.get("phones"), newUser);
        return newUser;
    }

    private static boolean validation(String stringToCheck, boolean checkMail){
        String regexPattern;
        if (checkMail){
            //OWASP Validation Regular Expression
            regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        } else {
            // ^                 # start-of-string
            // (?=.*[0-9])       # a digit must occur at least once
            // (?=.*[a-z])       # a lower case letter must occur at least once
            // (?=.*[A-Z])       # an upper case letter must occur at least once
            // (?=.*[@#$%^&+=])  # a special character must occur at least once
            // (?=\S+$)          # no whitespace allowed in the entire string
            // .{8,}             # anything, at least eight places though
            // $                 # end-of-string
            regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        }
        return Pattern.compile(regexPattern)
                .matcher(stringToCheck)
                .matches();
    }

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

    public ResponseEntity<User> getUserById(UUID uuid) {
        Optional<User> optionalUser = userRepository.findById(uuid);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<User> updateUserById(UUID uuid, User user) {
        Optional<User> oldUser = userRepository.findById(uuid);

        if (oldUser.isPresent()){
            User modifiedUser = oldUser.get();
            BeanUtils.copyProperties(user, modifiedUser);
            modifiedUser.setModified(LocalDateTime.now());
            modifiedUser.setLastLogin(LocalDateTime.now());
            userRepository.save(modifiedUser);
            return new ResponseEntity<>(modifiedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> deleteUserById(UUID uuid) {
        userRepository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
