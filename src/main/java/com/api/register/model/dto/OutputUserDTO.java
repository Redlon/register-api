package com.api.register.model.dto;

import com.api.register.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OutputUserDTO {
    private String uuid;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

    public static OutputUserDTO outputUserDTO(User user){
        OutputUserDTO userDTO = new OutputUserDTO();
        userDTO.setUuid(user.getUuid().toString());
        userDTO.setCreated(user.getCreated());
        userDTO.setModified(user.getModified());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setToken(user.getToken());
        userDTO.setIsActive(user.getIsActive());
        return userDTO;
    }
}
