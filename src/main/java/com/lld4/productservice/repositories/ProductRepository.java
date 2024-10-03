package com.lld4.productservice.repositories;

import com.lld4.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    public Optional<Product> findById(Long id);
    @Override
    public List<Product> findAll();

    @Override
    Product save(Product product);
}
