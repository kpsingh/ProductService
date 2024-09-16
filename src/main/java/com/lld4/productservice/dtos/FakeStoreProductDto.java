package com.lld4.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String imageUrl;
    private String category;

}
