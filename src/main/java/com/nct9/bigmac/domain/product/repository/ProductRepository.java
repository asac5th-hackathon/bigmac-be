package com.nct9.bigmac.domain.product.repository;

import com.nct9.bigmac.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByStoreIdAndCategoryId(Long storeId, Long categoryId);
    List<Product> findByStoreId(Long storeId);
}

