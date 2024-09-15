package com.lld4.productservice.services;

import com.lld4.productservice.dtos.FakeStoreProductDto;
import com.lld4.productservice.models.Category;
import com.lld4.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements  ProductService{

    RestTemplate restTemplate;
    RestClient restClient;


    FakeStoreProductService(RestTemplate restTemplate, RestClient restClient) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
    }

    @Override
    public Product getProductById(Long id) {

        //FakeStoreProductDto fakeStoreProductDto =   restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDto = restClient.get()
                .uri("https://fakestoreapi.com/products/" + id)
                .retrieve()
                .body(FakeStoreProductDto.class);

        System.out.println(fakeStoreProductDto);

        if (fakeStoreProductDto == null)
            return null;
        return convert(fakeStoreProductDto);


    }



    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDto = restClient.get().uri("https://fakestoreapi.com/products").retrieve().body(FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto1 : fakeStoreProductDto){
            Product product = convert(fakeStoreProductDto1);
            products.add(product);
        }
        System.out.println("Total products size : " + products.size());
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return List.of();
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    private Product convert(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImageUrl());

        Category category = new Category();
        category.setDescription(fakeStoreProductDto.getCategory());

        product.setCategory(category);
        return product;

    }
}
