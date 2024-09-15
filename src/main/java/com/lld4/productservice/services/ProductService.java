package com.lld4.productservice.services;

import com.lld4.productservice.models.Product;

import java.util.List;

public interface ProductService {
    public Product getProductById(Long id);
    public List<Product> getAllProducts();
    public List<Product> getProductsByCategoryId(Long categoryId);
    public Product addProduct(Product product);
    public Product updateProduct(Product product);
    public void deleteProduct(Long id);

}
