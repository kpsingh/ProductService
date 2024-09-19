package com.lld4.productservice.services;

import com.lld4.productservice.dtos.FakeStoreProductDto;
import com.lld4.productservice.models.Category;
import com.lld4.productservice.models.Product;
import com.lld4.productservice.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;



import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;
    RestClient restClient;

    private final String url = "https://fakestoreapi.com/products/";


    FakeStoreProductService(RestTemplate restTemplate, RestClient restClient) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
    }

    @Override
    public Product getProductById(Long id) {

        //FakeStoreProductDto fakeStoreProductDto =   restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDto = restClient.get().uri("https://fakestoreapi.com/products/" + id).retrieve().body(FakeStoreProductDto.class);

        if (fakeStoreProductDto == null)
            throw new UserNotFoundException("user not found for id " + id);

        return convertfromFakeProductDTOToProduct(fakeStoreProductDto);

    }


    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDto = restClient.get().uri("https://fakestoreapi.com/products").retrieve().body(FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto1 : fakeStoreProductDto) {
            Product product = convertfromFakeProductDTOToProduct(fakeStoreProductDto1);
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
        FakeStoreProductDto fakeStoreProductDto = convertfromProductToFakeStoreProductDTO(product);
        FakeStoreProductDto body = restClient.post().uri(url).contentType(APPLICATION_JSON).body(fakeStoreProductDto).retrieve().body(FakeStoreProductDto.class);

        if (body == null) return null;
        return convertfromFakeProductDTOToProduct(body);

    }

    // We'll use the PUT method here, since we want to replace the product completely.
    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertfromProductToFakeStoreProductDTO(product);

        // Via RestTemplate
        // We'll be directly calling the execute method of rest template for the PUT case. Underlying execute method is getting called from other methods , so we change it according to our need
        /*RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto restResponse = restTemplate.execute(url + id, HttpMethod.PUT, requestCallback, responseExtractor);*/

        // Via RestClient
        FakeStoreProductDto restResponse = restClient.put()
                .uri(url + id)
                .contentType(APPLICATION_JSON)
                .body(fakeStoreProductDto)
                .retrieve()
                .body(FakeStoreProductDto.class);
        return convertfromFakeProductDTOToProduct(restResponse);
    }


    // this will be a PATCH request
    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertfromProductToFakeStoreProductDTO(product);
        String fullUrl = url + id;

        FakeStoreProductDto restResponse = restClient.patch()  // Make a PATCH request
                .uri(fullUrl)
                .contentType(APPLICATION_JSON) // Set content type
                .body(fakeStoreProductDto)  // Pass the DTO as the body of the request
                .retrieve()  // Execute the request and expect a response
                .body(FakeStoreProductDto.class);

        return convertfromFakeProductDTOToProduct(restResponse);
    }


    @Override
    public Product deleteProduct(Long id) {
        FakeStoreProductDto body = restClient.delete().uri(url + id).retrieve().body(FakeStoreProductDto.class);
        System.out.println("Product Deleted Successfully");
        return convertfromFakeProductDTOToProduct(body);
    }

    private Product convertfromFakeProductDTOToProduct(FakeStoreProductDto fakeStoreProductDto) {
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

    private FakeStoreProductDto convertfromProductToFakeStoreProductDTO(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        if (product.getId() != null) fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImageUrl(product.getImageUrl());
        Category category = product.getCategory();
        if (category != null) {
            fakeStoreProductDto.setCategory(category.getDescription());
        }
        return fakeStoreProductDto;
    }
}
