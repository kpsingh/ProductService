package com.lld4.productservice;

import com.lld4.productservice.models.Product;
import com.lld4.productservice.repositories.ProductRepository;
import com.lld4.productservice.repositories.projections.ProductWithIDAndTitle;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

    Logger logger = LoggerFactory.getLogger(ProductServiceApplicationTests.class);


    @Autowired
    ProductRepository productRepository;

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

}
