package com.api.register.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue
    private Long phoneId;

    private String number;
    private String cityCode;
    private String countryCode;

    @ManyToOne
    @JoinColumn(name="uuid", nullable=false)
    @JsonIgnore
    private User user;
}
