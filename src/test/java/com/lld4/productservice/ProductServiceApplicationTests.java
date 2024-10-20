package com.lld4.productservice;

import com.lld4.productservice.models.Category;
import com.lld4.productservice.models.Product;
import com.lld4.productservice.repositories.CategoryRepository;
import com.lld4.productservice.repositories.ProductRepository;
import com.lld4.productservice.repositories.projections.ProductWithIDAndTitle;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

    Logger logger = LoggerFactory.getLogger(ProductServiceApplicationTests.class);


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCustomQuery() {
        List<Product> products = productRepository.findProductWithCustomQuery();
        for (Product product : products) {
            logger.info("Product name : {}, product price :{}", product.getTitle(), product.getPrice());
        }
    }

    @Test
    public void testProjection() {
        ProductWithIDAndTitle productWithIDAndTitle = productRepository.getProductWithIDAndTitle(252L);
        //System.out.println("Id : " + productWithIDAndTitle.getId() + ", title : " + productWithIDAndTitle.getTitle());

        System.out.println(productWithIDAndTitle.getId());
        System.out.println(productWithIDAndTitle.getTitle());
    }

    @Test
    public void testNative(){
        Product p = productRepository.custormGetProductById(352L);
        System.out.println(p.toString());
    }


    // test the case when we delete the categoty it will delete the phones associated with it.
    @Test
    public  void deleteByCatId(){
        categoryRepository.deleteById(3L);
    }

    @Test
    @Transactional // if we do not use this then we'll get the
    // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
    // com.lld4.productservice.models.Category.product: could not initialize proxy - no Session


    public  void testFetchTypes(){
        Optional<Category> categoryOptional = categoryRepository.findById(1L);
        System.out.println(categoryOptional.get());

        System.out.println("LAZY Fetch type");

        List<Product> categories = categoryOptional.get().getProduct();
        for (Product product : categories) {
            System.out.println(product);
        }

    }

}
