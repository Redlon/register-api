package com.api.register.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

}
