package com.lld4.productservice.services;

import com.lld4.productservice.exceptions.InvalidProductException;
import com.lld4.productservice.exceptions.ProductNotFoundException;
import com.lld4.productservice.models.Product;
import com.lld4.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("selfProductService")
public class SelfProductService implements  ProductService{

    ProductRepository productRepository;
    public SelfProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        //return productOptional.orElseThrow();

        if(productOptional.isPresent()){
            return productOptional.get();
        }else{
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return all;

    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return List.of();
    }

    @Override
    public Product addProduct(Product product) {

        return productRepository.save(product);

    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product oldProduct = getProductById(id);
        if(product == null)
            throw new InvalidProductException("Invalid product is passed ");

        // update the description
        if(product.getDescription() != null ){
            oldProduct.setDescription(product.getDescription());
        }

        // update the price
        if(product.getPrice() != oldProduct.getPrice()){
            oldProduct.setPrice(product.getPrice());
        }

        // similarly we can do the check for the other attribute of product to update them

      return   productRepository.save(oldProduct);

    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}
