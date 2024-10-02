package com.lld4.productservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String isDeleted;
}
