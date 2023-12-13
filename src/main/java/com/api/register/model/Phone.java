package com.api.register.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PHONES")
public class Phone {

    @Id
    @GeneratedValue
    private Long phoneId;

    private String number;
    private String cityCode;
    private String countryCode;

    @ManyToOne
    @JoinColumn(name="uuid", nullable=false)
    private User user;
}
