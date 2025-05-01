package com.lld4.productservice.controllers;

import com.lld4.productservice.auth.AuthUtils;
import com.lld4.productservice.dtos.ProductDTO;
import com.lld4.productservice.dtos.UserDto;
import com.lld4.productservice.exceptions.InvalidProductException;
import com.lld4.productservice.models.Category;
import com.lld4.productservice.models.Product;
import com.lld4.productservice.models.Role;
import com.lld4.productservice.models.Token;
import com.lld4.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller class for managing products.
 * Provides endpoints for CRUD operations and additional functionalities like authorization.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final AuthUtils authUtils;

    /**
     * Constructor for ProductController.
     *
     * @param productService The service layer for product-related operations.
     * @param authUtils      Utility class for authentication and authorization.
     */
    ProductController(@Qualifier("selfProductService") ProductService productService, AuthUtils authUtils) {
        this.productService = productService;
        this.authUtils = authUtils;
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the product or an error status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        if (id < 1) {
            throw new InvalidProductException("Invalid product id : " + id);
        }
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Retrieves a product by its ID with authorization checks.
     * Only accessible by authorized users with the ADMIN role.
     *
     * @param token The token for user authentication.
     * @param id    The ID of the product to retrieve.
     * @return ResponseEntity containing the product or an error status.
     */
    @PostMapping("/v1")
    public ResponseEntity<Product> getProductByIdV1(@RequestBody Token token, @RequestParam Long id) {
        UserDto userDto = authUtils.validateToken(token);

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean isAdmin = false;
        for (Role role : userDto.getRoles()) {
            if (role.getRoleName().equalsIgnoreCase("ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Retrieves all products.
     * Only accessible by authorized users with the ADMIN role.
     *
     * @param token The token for user authentication.
     * @return ResponseEntity containing the list of products or an error status.
     */
    public ResponseEntity<List<Product>> getAllProducts(@RequestBody Token token) {
        UserDto userDto = authUtils.validateToken(token);

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean isAdmin = false;
        for (Role role : userDto.getRoles()) {
            if (role.getRoleName().equalsIgnoreCase("ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Adds a new product.
     *
     * @param productDto The DTO containing product details.
     * @return The added product.
     */
    @PostMapping
    public Product addProduct(@RequestBody ProductDTO productDto) {
        Product product = getProductFromDto(productDto);
        return productService.addProduct(product);
    }

    /**
     * Partially updates a product by its ID.
     *
     * @param id      The ID of the product to update.
     * @param product The product details to update.
     * @return The updated product.
     */
    @PatchMapping("/{productId}")
    public Product updateProductbyId(@PathVariable("productId") Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    /**
     * Completely replaces a product by its ID.
     *
     * @param id      The ID of the product to replace.
     * @param product The new product details.
     * @return The replaced product.
     */
    @PutMapping("/{productId}")
    public Product replaceProductById(@PathVariable("productId") Long id, @RequestBody Product product) {
        return productService.replaceProduct(id, product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return The deleted product.
     */
    @DeleteMapping("/{productId}")
    public Product deleteProductById(@PathVariable("productId") Long id) {
        return productService.deleteProduct(id);
    }

    /**
     * Converts a ProductDTO to a Product entity.
     *
     * @param productDto The DTO containing product details.
     * @return The Product entity.
     */
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