package com.lld4.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Product extends BaseModel{
    private String title;
    private double price;
    private String description;
    private String imageUrl;
    @ManyToOne
    private Category category;

}

/*
    Product --- Category
    1       :   1
    M       :   1
    ---------------
    M   : 1
 */
