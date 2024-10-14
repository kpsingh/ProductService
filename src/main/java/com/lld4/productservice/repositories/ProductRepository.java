package com.lld4.productservice.repositories;

import com.lld4.productservice.models.Product;
import com.lld4.productservice.repositories.projections.ProductWithIDAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // an example of custom query - HQL - Hibernate Query Language
    @Query("select p from Product p where p.price > 100000")
    List<Product> findProductWithCustomQuery();



    /* an example of Projection:
        We use projection when we don't want to fetch whole attributes but wat only few attributes

        Using alias here is  MUST otherwise value will not be picked.
     */

    @Query("select p.id as id, p.title as title from Product p where p.id = :id")
    ProductWithIDAndTitle getProductWithIDAndTitle(Long id);



    /*
        Native Query : we have to make native as true
     */

    @Query(value = "select * from product p where p.id = 352", nativeQuery = true)
    Product custormGetProductById(Long id);

}
