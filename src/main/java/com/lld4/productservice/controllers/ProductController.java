package com.lld4.productservice.controllers;

import com.lld4.productservice.dtos.ProductDTO;
import com.lld4.productservice.exceptions.InvalidProductException;
import com.lld4.productservice.models.Category;
import com.lld4.productservice.models.Product;
import com.lld4.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    ProductController(@Qualifier("selfProductService") ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:9090/products/5
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        if (id < 1) {
            throw new InvalidProductException("Invalid product id : " + id);
        }
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDTO productDto) {
        Product product = getProductFromDto(productDto);
        return productService.addProduct(product);
    }



    // Partial Update - PATCH
    @PatchMapping("/{productId}")
    public Product updateProductbyId(@PathVariable("productId") Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // Complete Update - PUT
    @PutMapping("/{productId}")
    public Product replaceProductById(@PathVariable("productId") Long id, @RequestBody Product product) {
        return productService.replaceProduct(id, product);

    }

    @DeleteMapping("/{productId}")
    public Product deleteProductById(@PathVariable("productId") Long id) {
        return productService.deleteProduct(id);
    }

    private Product getProductFromDto(ProductDTO productDto) {

        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());

        Category category = new Category();
        if (productDto.getCategory() != null) {
            category.setTitle(productDto.getCategory().getTitle());
            category.setDescription(productDto.getCategory().getDescription());
        }

        product.setCategory(category);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setIsDeleted(Boolean.FALSE);

        return product;
    }
}
