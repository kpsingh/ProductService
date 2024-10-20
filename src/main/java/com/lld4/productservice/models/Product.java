package com.lld4.productservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@ToString
@Entity
@JsonIgnoreProperties
public class Product extends BaseModel {
    private String title;
    private double price;
    private String description;
    private String imageUrl;

    // @ManyToOne(cascade = CascadeType.ALL) // this will make sure when we save the product it will automatically first save the Category object,
    // otherwise transient state exception could have came./ this will do lelete as well, if you delete product then categoty also get delited
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // it only applicable to saving object
    private Category category;

}

/*
    Product --- Category
    1       :   1
    M       :   1
    ---------------
    M   : 1
 */
