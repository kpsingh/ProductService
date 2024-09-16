package com.lld4.productservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String imageUrl;
    private Category category;

}
