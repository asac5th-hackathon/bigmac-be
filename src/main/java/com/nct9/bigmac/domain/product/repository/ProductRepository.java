package com.nct9.bigmac.domain.product.repository;

import com.nct9.bigmac.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByStoreIdAndCategoryId(Long storeId, Long categoryId);
    List<Product> findByStoreId(Long storeId);
    Optional<Product> findByName(String productName);
}