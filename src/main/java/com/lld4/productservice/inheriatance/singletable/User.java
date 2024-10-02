package com.lld4.productservice.inheriatance.singletable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity(name = "st_users")
public class User {
    @Id
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String phone;
}