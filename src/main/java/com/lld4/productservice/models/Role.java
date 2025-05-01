package com.lld4.productservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends BaseModel{
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}


