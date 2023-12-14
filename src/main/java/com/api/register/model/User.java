package com.api.register.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Entity
@Table(name="USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Phone> phoneList;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;

    @Lob
    private String token;

    private Boolean isActive = true;

    public static boolean mailValidation(String email){
        //OWASP Validation Regular Expression
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

}
