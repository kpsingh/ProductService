package com.lld4.productservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@ToString
@Entity
@JsonIgnoreProperties
public class Product extends BaseModel{
    private String title;
    private double price;
    private String description;
    private String imageUrl;
    @ManyToOne(cascade = CascadeType.ALL) // this will make sure when we save the product it will automatically first save the Category object,
    // otherwise transient state exception could have came
    private Category category;

}

/*
    Product --- Category
    1       :   1
    M       :   1
    ---------------
    M   : 1
 */
