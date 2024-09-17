package com.lld4.productservice.services;

import com.lld4.productservice.models.Product;

import java.util.List;

public interface ProductService {
    public Product getProductById(Long id);
    public List<Product> getAllProducts();
    public List<Product> getProductsByCategoryId(Long categoryId);
    public Product addProduct(Product product);

    // completely replace the product with new product details
    public Product replaceProduct(Long id, Product product);

    // this will partially update the product
    public Product updateProduct(Long id, Product product);

    public Product deleteProduct(Long id);

}
