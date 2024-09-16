package com.lld4.productservice.controllers;

import com.lld4.productservice.models.Product;
import com.lld4.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:9090/products/5
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
       return productService.addProduct(product);
    }

    // Partial Update - PATCH
    @PatchMapping("/{productId}")
    public Product updateProductbyId(@PathVariable("productId") Long id, @RequestBody Product product) {
        return product;
    }

    // Complete Update - PUT
    @PutMapping("/{productId}")
    public void replaceProductById(@PathVariable("productId") String id, @RequestBody Product product) {

    }

    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable("productId") Long id) {

    }
}
