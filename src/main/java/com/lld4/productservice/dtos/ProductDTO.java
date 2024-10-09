package com.lld4.productservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String title;
    private double price;
    private String description;
    private String imageUrl;
    private CategoryDTO category;
}
